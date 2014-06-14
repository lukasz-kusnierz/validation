package org.lukaszkusnierz.validation.validator.string;

import com.google.common.base.Objects;
import org.lukaszkusnierz.validation.chain.ValidationChain;

public class StringValidators {

	private ValidationChain<String> chain = new ValidationChain<>();

	public ValidationChain<String> chain() {
		return this.chain;
	}

	public StringValidators notEmpty() {
		return this.minLength( 1 );
	}

	public StringValidators minLength( final int minLength ) {
		this.chain.add( subject -> null != subject && subject.length() >= minLength );
		return this;
	}

	public StringValidators maxLength( final int maxLength ) {
		this.chain.add( subject -> null != subject && subject.length() <= maxLength );
		return this;
	}

	public StringValidators exactLength( final int length ) {
		this.chain.add( subject -> null != subject && subject.length() == length );
		return this;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( chain );
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null || getClass() != obj.getClass() ) {
			return false;
		}
		final StringValidators other = ( StringValidators ) obj;
		return Objects.equal( this.chain, other.chain );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "chain", chain )
				.toString();
	}
}
