package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.result.Invalid;
import org.lukaszkusnierz.validation.result.Valid;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public final class Validation<T> {

	private final LinkedList<SimpleValidationEntry<T, ?>> entries = new LinkedList<>();
	private boolean breakOnAnyFailure = false;

	private Validation() {
	}

	public static <T> Validation<T> of( final Class<T> c ) {
		if ( null == c ) {
			throw new IllegalArgumentException( "Class cannot be null, please provide the type you are going to validate or use Object.class when desperate" );
		}
		return new Validation<>();
	}

	public <FIELD> ValidationEntryBuilder<T, FIELD> validate( final Function<T, FIELD> useMethodReferenceSyntax ) {
		return new ValidationEntryBuilder( this, useMethodReferenceSyntax );
	}

	public Validation<T> add( final SimpleValidationEntry<T, ?> entry ) {
		this.entries.add( entry );
		return this;
	}

	public Validation<T> breakOnFailure() {
		if ( this.entries.isEmpty() ) {
			throw new IllegalStateException( "Add some validation before you use breakOnFailure(), or maybe, did you mean breakOnAnyFailure() ?" );
		}
		this.entries.getLast().breakOnFailure();
		return this;
	}

	public Validation<T> breakOnAnyFailure() {
		this.breakOnAnyFailure = true;
		return this;
	}

	public Validated<T> go( final T subject ) {
		final List<Validated<?>> invalid = new LinkedList<>();
		for ( final SimpleValidationEntry<T, ?> entry : this.entries ) {
			final Validated<?> validated = entry.validate( subject );
			if ( validated.isValid() ) {
				continue;
			}
			invalid.add( validated );
			if ( entry.isBreakOnFailure() ) {
				break;
			}
			if ( this.breakOnAnyFailure ) {
				break;
			}
		}
		return invalid.isEmpty() ? new Valid<>( subject ) : new Invalid<>( subject );
	}
}
