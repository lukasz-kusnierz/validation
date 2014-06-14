package org.lukaszkusnierz.validation;

import com.google.common.base.Preconditions;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.LinkedList;
import java.util.function.Function;

public class Validation<T> {

	private LinkedList<ValidationEntry<T, ?>> entries = new LinkedList<>();

	private Validation() {
	}

	public static <T> Validation<T> of( final Class<T> c ) {
		return new Validation<T>();
	}

	public <FIELD> ValidationEntryBuilder<T, FIELD> validate( final Function<T, FIELD> useMethodReferenceSyntax ) {
		return new ValidationEntryBuilder( this, useMethodReferenceSyntax );
	}

	public Validation<T> add( final ValidationEntry<T, ?> entry ) {
		this.entries.add( entry );
		return this;
	}

	public Validation<T> breakOnFailure() {
		Preconditions.checkState( !this.entries.isEmpty(), "Add some validation before you use 'breakOnFailure'" );
		this.entries.getLast().breakOnFailure();
		return this;
	}

	public ValidationResult<T> go( final T subject ) {
		final ValidationResult<T> validationResult = new ValidationResult<>();
		for ( final ValidationEntry<T, ?> entry : this.entries ) {
			final Validated<?> validated = entry.validate( subject );
			validationResult.add( validated );
			if ( validated.isInvalid() && entry.isBreakOnFailure() ) {
				break;
			}
		}
		return validationResult;
	}
}
