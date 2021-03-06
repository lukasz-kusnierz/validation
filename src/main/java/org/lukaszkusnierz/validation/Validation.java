package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.result.internal.ValidatedInvalid;
import org.lukaszkusnierz.validation.result.internal.ValidatedValid;
import org.lukaszkusnierz.validation.result.Validated;
import org.lukaszkusnierz.validation.validator.NotNullValidator;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

/**
 * Used to setup a validation of complex object, containing of multiple values being validated separately.
 *
 * @param <T> type of object under validation
 */
public final class Validation<T> {

	private final LinkedList<ValidationEntry<T, ?>> entries = new LinkedList<>();
	private boolean breakOnAnyFailure = false;
	private ValidationChain<T> validationChain = ValidationChain.empty();

	private Validation() {
	}

	/**
	 * Creates Validation instance. There is no other way to do it.
	 *
	 * @param typeYouWantToValidate type you want to validate
	 * @param <T>                   type you validate
	 * @return Validation object
	 */
	public static <T> Validation<T> of( final Class<T> typeYouWantToValidate ) {
		if ( null == typeYouWantToValidate ) {
			throw new IllegalArgumentException( "Class cannot be null, please provide the type you are going to validate or use Object.class when desperate" );
		}
		return new Validation<>();
	}

	public Validation<T> use( final ValidationChain<T> validationChain ) {
		if ( null == validationChain ) {
			throw new IllegalArgumentException( "ValidationChain cannot be null" );
		}
		this.validationChain = validationChain;
		return this;
	}

	public Validation<T> notNull() {
		this.validationChain.add( new NotNullValidator<>() );
		return this;
	}

	/**
	 * @param useMethodReferenceSyntax function extracting a field from the object under validation; use method reference syntax for readability
	 * @param <FIELD>                  type of the field extracted from the object under validation
	 * @return
	 */
	public <FIELD> ValidationEntryBuilder<T, FIELD> validate( final Function<T, FIELD> useMethodReferenceSyntax ) {
		return new ValidationEntryBuilder( this, useMethodReferenceSyntax );
	}

	/**
	 * @param useMethodReferenceSyntax function extracting an <strong>iterable</strong> field from the object under validation; use method reference syntax for readability
	 * @param <ITERABLE>               iterable type of the field
	 * @param <FIELD>                  type of the iterable
	 * @return
	 */
	public <ITERABLE extends Iterable<FIELD>, FIELD> IterableValidationEntryBuilder<T, FIELD, ITERABLE> validateAll( final Function<T, ITERABLE> useMethodReferenceSyntax ) {
		return new IterableValidationEntryBuilder<>( this, useMethodReferenceSyntax );
	}

	public Validation<T> add( final ValidationEntry<T, ?> entry ) {
		this.entries.add( entry );
		return this;
	}

	/**
	 * Validation will finish immediately if previous validator has failed.
	 *
	 * @return Validation object
	 */
	public Validation<T> breakOnFailure() {
		if ( this.entries.isEmpty() ) {
			throw new IllegalStateException( "Add some validation before you use breakOnFailure(), or maybe, did you mean breakOnAnyFailure() ?" );
		}
		this.entries.getLast().breakOnFailure();
		return this;
	}

	/**
	 * Validation will finish immediately whenever any Validator fails. By default, all validators are executed, even if some of them fail.
	 *
	 * @return Validation object
	 */
	public Validation<T> breakOnAnyFailure() {
		this.breakOnAnyFailure = true;
		return this;
	}

	public Validation<T> otherwise( final String message ) {
		this.validationChain.otherwise( message );
		return this;
	}

	public Validation<T> otherwise( final String format, final Object... params ) {
		this.validationChain.otherwise( format, params );
		return this;
	}

	public Validation<T> otherwise( final Function<T, String> failureMessageFactory ) {
		this.validationChain.otherwise( failureMessageFactory );
		return this;
	}

	/**
	 * Triggers the validation process.
	 *
	 * @param subject object to be validated
	 * @return Validated object
	 */
	public Validated<T> go( final T subject ) {
		boolean invalid = false;
		final List<String> failureMessages = new LinkedList<>();

		final Validated<T> validatedSubject = validationChain.validate( subject );
		if ( validatedSubject.isInvalid() ) {
			failureMessages.addAll( validatedSubject.getFailureMessages() );
			if ( this.breakOnAnyFailure ) {
				return new ValidatedInvalid<T>( subject, failureMessages );
			}
		}

		for ( final ValidationEntry<T, ?> entry : this.entries ) {
			final Validated<?> validated = entry.validate( subject );
			if ( validated.isValid() ) {
				continue;
			}
			failureMessages.addAll( validated.getFailureMessages() );
			invalid = true;
			if ( entry.isBreakOnFailure() ) {
				break;
			}
			if ( this.breakOnAnyFailure ) {
				break;
			}
		}
		return invalid ? new ValidatedInvalid<>( subject, failureMessages ) : new ValidatedValid<>( subject );
	}
}
