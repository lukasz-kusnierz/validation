package org.lukaszkusnierz.validation.chain;

import org.lukaszkusnierz.validation.result.Invalid;
import org.lukaszkusnierz.validation.result.Valid;
import org.lukaszkusnierz.validation.result.Validated;
import org.lukaszkusnierz.validation.validator.NotNullValidator;
import org.lukaszkusnierz.validation.validator.Validator;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.function.Function;

public final class ValidationChain<T> {

	private final LinkedList<ValidationChainEntry<T>> entries = new LinkedList<>();
	private boolean breakOnAnyFailure = false;

	private ValidationChain() {
	}

	public static <T> ValidationChain<T> use( final Validator<T> validator ) {
		return new ValidationChain<T>().add( validator );
	}

	public static <T> ValidationChain<T> notNull() {
		return new ValidationChain<T>().add( new NotNullValidator<>() );
	}

	public static <T> ValidationChain<T> empty() {
		return new ValidationChain<>();
	}

	public ValidationChain<T> breakOnAnyFailure() {
		this.breakOnAnyFailure = true;
		return this;
	}

	public ValidationChain<T> breakOnFailure() {
		if ( this.entries.isEmpty() ) {
			throw new IllegalStateException( "You have to add at least one validator before you use breakOnFailure(), or maybe, did you mean breakOnAnyFailure() ?" );
		}
		this.entries.getLast().breakOnFailure();
		return this;
	}

	public ValidationChain<T> otherwise( final String message ) {
		if ( this.entries.isEmpty() ) {
			throw new IllegalStateException( "You have to add at least one validator before you use otherwise(...)" );
		}
		if ( null == message ) {
			throw new IllegalArgumentException( "Failure message cannot be null" );
		}
		this.entries.getLast().otherwise( ( value ) -> message );
		return this;
	}

	public ValidationChain<T> otherwise( final String format, final Object... params ) {
		if ( this.entries.isEmpty() ) {
			throw new IllegalStateException( "You have to add at least one validator before you use otherwise(...)" );
		}
		if ( null == format ) {
			throw new IllegalArgumentException( "Failure message format cannot be null" );
		}
		this.entries.getLast().otherwise( ( value ) -> String.format( format, params ) );
		return this;
	}

	public ValidationChain<T> otherwise( final Function<T, String> failureMessageFactory ) {
		if ( this.entries.isEmpty() ) {
			throw new IllegalStateException( "You have to add at least one validator before you use otherwise(...)" );
		}
		if ( null == failureMessageFactory ) {
			throw new IllegalArgumentException( "Failure message factory cannot be null" );
		}
		this.entries.getLast().otherwise( failureMessageFactory );
		return this;
	}

	public ValidationChain<T> add( final Validator<T> validator ) {
		this.entries.add( new ValidationChainEntry<>( validator ) );
		return this;
	}

	public Validated<T> validate( T subject ) {
		final List<Validator<T>> failedValidators = new LinkedList<>();
		final List<String> failureMessages = new LinkedList<>();
		for ( final ValidationChainEntry<T> entry : this.entries ) {
			final boolean isValid = entry.isValid( subject );
			if ( isValid ) {
				continue;
			}
			failedValidators.add( entry.getValidator() );
			final Optional<String> failureMessage = entry.getFailureMessage( subject );
			if ( failureMessage.isPresent() ) {
				failureMessages.add( failureMessage.get() );
			}
			if ( this.breakOnAnyFailure ) {
				break;
			}
			if ( entry.isBreakOnFailure() ) {
				break;
			}
		}
		if ( failedValidators.isEmpty() ) {
			return new Valid<>( subject );
		}
		return new Invalid<>( subject, failureMessages );
	}

	@Override
	public int hashCode() {
		return Objects.hash( entries, breakOnAnyFailure );
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null || getClass() != obj.getClass() ) {
			return false;
		}
		final ValidationChain other = ( ValidationChain ) obj;
		return Objects.equals( this.entries, other.entries ) && Objects.equals( this.breakOnAnyFailure, other.breakOnAnyFailure );
	}
}
