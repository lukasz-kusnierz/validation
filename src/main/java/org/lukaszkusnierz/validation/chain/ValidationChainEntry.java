package org.lukaszkusnierz.validation.chain;

import org.lukaszkusnierz.validation.validator.Validator;

import java.util.Objects;

final class ValidationChainEntry<T> {

	private final Validator<T> validator;
	private boolean breakOnFailure = false;
	private boolean breakOnError;

	ValidationChainEntry( final Validator<T> validator ) {
		if ( null == validator ) {
			throw new IllegalArgumentException( "Validator just cannot be null, you have no excuse" );
		}
		this.validator = validator;
	}

	public boolean isValid( T subject ) {
		return this.validator.isValid( subject );
	}

	public Validator<T> getValidator() {
		return this.validator;
	}

	public boolean isBreakOnError() {
		return this.breakOnError;
	}

	public void breakOnFailure() {
		this.breakOnFailure = true;
	}

	@Override
	public int hashCode() {
		return Objects.hash( validator, breakOnFailure, breakOnError );
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null || getClass() != obj.getClass() ) {
			return false;
		}
		final ValidationChainEntry other = ( ValidationChainEntry ) obj;
		return Objects.equals( this.validator, other.validator ) && Objects.equals( this.breakOnFailure, other.breakOnFailure ) && Objects.equals( this.breakOnError, other.breakOnError );
	}
}
