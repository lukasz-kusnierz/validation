package org.lukaszkusnierz.validation.result;

import org.lukaszkusnierz.validation.exception.CheckedValidationException;
import org.lukaszkusnierz.validation.exception.RuntimeValidationException;

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
	public T orNull() {
		return null;
	}

	@Override
	public OrThrow<T> orThrow() {
		return this;
	}

	@Override
	public T checkedException() throws CheckedValidationException {
		throw new CheckedValidationException();
	}

	@Override
	public <EX extends Exception> T checkedException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null, use method reference syntax to reference a constructor of your favourite exception, ex: orThrow().checkedException( IOException::new )" );
		}
		throw exceptionSupplier.get();
	}

	@Override
	public T runtimeException() throws RuntimeValidationException {
		throw new RuntimeValidationException();
	}

	@Override
	public <EX extends RuntimeException> T runtimeException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null, use method reference syntax to reference a constructor of your favourite exception, ex: orThrow().runtimeException( NumberFormatException::new )" );
		}
		throw exceptionSupplier.get();
	}

	@Override
	public boolean equalReference( final Validated<T> another ) {
		if ( null == another ) {
			return false;
		}
		return Objects.equals( this.reference, another.get() );
	}

	@Override
	public boolean equalReference( final T another ) {
		return Objects.equals( this.reference, another );
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
