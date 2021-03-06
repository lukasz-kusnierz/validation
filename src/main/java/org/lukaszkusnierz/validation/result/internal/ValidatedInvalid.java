package org.lukaszkusnierz.validation.result.internal;

import org.lukaszkusnierz.validation.exception.CheckedValidationException;
import org.lukaszkusnierz.validation.exception.RuntimeValidationException;
import org.lukaszkusnierz.validation.result.*;

import java.util.*;
import java.util.function.Function;
import java.util.function.Supplier;

public final class ValidatedInvalid<T> implements Validated<T>, OrThrow<T> {

	private final T reference;
	private final List<String> failureMessages;

	public ValidatedInvalid(final T reference, final List<String> failureMessages) {
		this.reference = reference;
		this.failureMessages = null == failureMessages ? Collections.EMPTY_LIST : new ArrayList<>( failureMessages );
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
	public T or( final Supplier<T> supplier ) {
		if ( null == supplier ) {
			throw new IllegalArgumentException( "Supplier cannot be null. Please note you can use method reference syntax to reference a constructor, ex: or( AtomicInteger::new )" );
		}
		return supplier.get();
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
	public org.lukaszkusnierz.validation.result.Valid<T> checkedException() throws CheckedValidationException {
		throw new CheckedValidationException( this.failureMessages );
	}

	@Override
	public <EX extends Exception> org.lukaszkusnierz.validation.result.Valid<T> checkedException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null, use method reference syntax to reference a constructor of your favourite exception, ex: orThrow().checkedException( IOException::new )" );
		}
		throw exceptionSupplier.get();
	}

	@Override
	public org.lukaszkusnierz.validation.result.Valid<T> runtimeException() throws RuntimeValidationException {
		throw new RuntimeValidationException( this.failureMessages );
	}

	@Override
	public <EX extends RuntimeException> org.lukaszkusnierz.validation.result.Valid<T> runtimeException( final Supplier<EX> exceptionSupplier ) throws EX {
		if ( null == exceptionSupplier ) {
			throw new IllegalArgumentException( "Exception supplier cannot be null, use method reference syntax to reference a constructor of your favourite exception, ex: orThrow().runtimeException( NumberFormatException::new )" );
		}
		throw exceptionSupplier.get();
	}

	@Override
	public Optional<T> asOptional() {
		return Optional.empty();
	}

	@Override
	public <TARGET> Validated<TARGET> map( final Function<T, TARGET> mapper ) {
		if ( null == mapper ) {
			throw new IllegalArgumentException( "Mapping function cannot be null. Use lambda or method reference syntax for short implementation" );
		}
		return new ValidatedInvalid<>( mapper.apply( this.reference ), this.failureMessages );
	}

	@Override
	public List<String> getFailureMessages() {
		return this.failureMessages.isEmpty() ? Collections.EMPTY_LIST : new ArrayList<>( this.failureMessages );
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
		final ValidatedInvalid other = (ValidatedInvalid) obj;
		return Objects.equals( this.reference, other.reference );
	}
}
