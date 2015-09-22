package org.lukaszkusnierz.validation.result.internal;

import org.lukaszkusnierz.validation.exception.CheckedValidationException;
import org.lukaszkusnierz.validation.exception.RuntimeValidationException;
import org.lukaszkusnierz.validation.result.OrThrow;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ValidatedValid<T> implements Validated<T>, OrThrow<T> {

	private final T reference;

	public ValidatedValid(final T reference) {
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
	public org.lukaszkusnierz.validation.result.Valid<T> checkedException() throws CheckedValidationException {
		return new org.lukaszkusnierz.validation.result.Valid<>(reference);
	}

	@Override
	public <EX extends Exception> org.lukaszkusnierz.validation.result.Valid<T> checkedException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null, use method reference syntax to reference a constructor of your favourite exception, ex: orThrow().checkedException( IOException::new )" );
		}
		return new org.lukaszkusnierz.validation.result.Valid<T>(reference);
	}

	@Override
	public org.lukaszkusnierz.validation.result.Valid<T> runtimeException() throws RuntimeValidationException {
		return new org.lukaszkusnierz.validation.result.Valid<T>(reference);
	}

	@Override
	public <EX extends RuntimeException> org.lukaszkusnierz.validation.result.Valid<T> runtimeException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null, use method reference syntax to reference a constructor of your favourite exception, ex: orThrow().runtimeException( NumberFormatException::new )" );
		}
		return new org.lukaszkusnierz.validation.result.Valid<T>(reference);
	}

	@Override
	public Optional<T> asOptional() {
		return Optional.ofNullable( this.reference );
	}

	@Override
	public <TARGET> Validated<TARGET> map( final Function<T, TARGET> mapper ) {
		if ( null == mapper ) {
			throw new IllegalArgumentException( "Mapping function cannot be null. Use lambda or method reference syntax for short implementation" );
		}
		return new ValidatedValid<>( mapper.apply( this.reference ) );
	}

	@Override
	public List<String> getFailureMessages() {
		return Collections.emptyList();
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
		final ValidatedValid other = (ValidatedValid) obj;
		return Objects.equals( this.reference, other.reference );
	}
}
