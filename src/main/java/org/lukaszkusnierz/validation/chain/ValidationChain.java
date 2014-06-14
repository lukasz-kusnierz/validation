package org.lukaszkusnierz.validation.chain;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.lukaszkusnierz.validation.result.Invalid;
import org.lukaszkusnierz.validation.result.Valid;
import org.lukaszkusnierz.validation.result.Validated;
import org.lukaszkusnierz.validation.validator.NotNullValidator;
import org.lukaszkusnierz.validation.validator.Validator;

import java.util.LinkedList;
import java.util.List;

public final class ValidationChain<T> {

	private final LinkedList<ValidationChainEntry<T>> entries = new LinkedList<>();
	private boolean breakOnAnyFailure = false;

	public static <T> ValidationChain<T> use( final Validator<T> validator ) {
		return new ValidationChain<T>().then( validator );
	}

	public static <T> ValidationChain<T> notNull() {
		return new ValidationChain<T>().then( new NotNullValidator<>() );
	}

	public ValidationChain<T> breakOnAnyFailure() {
		this.breakOnAnyFailure = true;
		return this;
	}

	public ValidationChain<T> breakOnFailure() {
		Preconditions.checkState( !this.entries.isEmpty(), "You have to add at least one validator before you set 'breakOnFailure'" );
		this.entries.getLast().breakOnFailure();
		return this;
	}

	public ValidationChain<T> then( final Validator<T> validator ) {
		this.entries.add( new ValidationChainEntry<T>( validator ) );
		return this;
	}

	public Validated<T> validate( T subject ) {
		final List<Validator<T>> failedValidators = new LinkedList<>();
		for ( final ValidationChainEntry<T> entry : this.entries ) {
			final boolean isValid = entry.isValid( subject );
			if ( isValid ) {
				continue;
			}
			failedValidators.add( entry.getValidator() );
			if ( this.breakOnAnyFailure ) {
				break;
			}
			if ( entry.isBreakOnError() ) {
				break;
			}
		}
		if ( failedValidators.isEmpty() ) {
			return new Valid<>( subject );
		}
		return new Invalid<>( subject );
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( entries, breakOnAnyFailure );
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
		return Objects.equal( this.entries, other.entries ) && Objects.equal( this.breakOnAnyFailure, other.breakOnAnyFailure );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "entries", entries )
				.add( "breakOnAnyFailure", breakOnAnyFailure )
				.toString();
	}
}
