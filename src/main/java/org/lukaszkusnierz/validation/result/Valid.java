package org.lukaszkusnierz.validation.result;

import org.lukaszkusnierz.validation.exception.CheckedValidationException;
import org.lukaszkusnierz.validation.exception.RuntimeValidationException;

import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

public final class Valid<T> implements Validated<T>, OrThrow<T> {

	private final T reference;

	public Valid( final T reference ) {
		this.reference = reference;
	}

	@Override
	public T get() {
		return this.reference;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean isInvalid() {
		return false;
	}

	@Override
	public T or( final T alternative ) {
		return this.reference;
	}

	@Override
	public T or( final Supplier<T> supplier ) {
		if ( null == supplier ) {
			throw new IllegalArgumentException( "Supplier cannot be null. Please note you can use method reference syntax to reference a constructor, ex: or( AtomicInteger::new )" );
		}
		return this.reference;
	}

	@Override
	public T orNull() {
		return this.reference;
	}

	@Override
	public OrThrow<T> orThrow() {
		return this;
	}

	@Override
	public T checkedException() throws CheckedValidationException {
		return this.reference;
	}

	@Override
	public <EX extends Exception> T checkedException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null, use method reference syntax to reference a constructor of your favourite exception, ex: orThrow().checkedException( IOException::new )" );
		}
		return this.reference;
	}

	@Override
	public T runtimeException() throws RuntimeValidationException {
		return this.reference;
	}

	@Override
	public <EX extends RuntimeException> T runtimeException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null, use method reference syntax to reference a constructor of your favourite exception, ex: orThrow().runtimeException( NumberFormatException::new )" );
		}
		return this.reference;
	}

	@Override
	public <TARGET> Validated<TARGET> map( final Function<T, TARGET> mapper ) {
		if ( null == mapper ) {
			throw new IllegalArgumentException( "Mapping function cannot be null. Use lambda or method reference syntax for short implementation" );
		}
		return new Valid<>( mapper.apply( this.reference ) );
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
		final Valid other = ( Valid ) obj;
		return Objects.equals( this.reference, other.reference );
	}
}
