package org.lukaszkusnierz.validation.result;

import org.lukaszkusnierz.validation.exception.ValidationException;

import java.util.Objects;
import java.util.function.Supplier;

public final class Invalid<T> implements Validated<T>, OrThrow<T> {

	private final T reference;

	public Invalid( final T reference ) {
		this.reference = reference;
	}

	@Override
	public T get() {
		return this.reference;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean isInvalid() {
		return true;
	}

	@Override
	public T or( final T alternative ) {
		return alternative;
	}

	@Override
	public OrThrow<T> orThrow() {
		return this;
	}

	@Override
	public T checkedException() throws ValidationException {
		throw new ValidationException();
	}

	@Override
	public <EX extends Exception> T checkedException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null" );
		}
		throw exceptionSupplier.get();
	}

	@Override
	public int hashCode() {
		return Objects.hash( reference );
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null || getClass() != obj.getClass() ) {
			return false;
		}
		final Invalid other = ( Invalid ) obj;
		return Objects.equals( this.reference, other.reference );
	}
}
