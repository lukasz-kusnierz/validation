package org.lukaszkusnierz.validation.chain;

import com.google.common.base.Objects;
import com.google.common.base.Preconditions;
import org.lukaszkusnierz.validation.validator.Validator;

final class ValidationChainEntry<T> {

	private final Validator<T> validator;
	private boolean breakOnFailure = false;
	private boolean breakOnError;

	ValidationChainEntry( final Validator<T> validator ) {
		Preconditions.checkArgument( null != validator, "Validator cannot be null" );
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
		return Objects.hashCode( validator, breakOnFailure );
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
		return Objects.equal( this.validator, other.validator ) && Objects.equal( this.breakOnFailure, other.breakOnFailure );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "validator", validator )
				.add( "breakOnFailure", breakOnFailure )
				.toString();
	}
}
