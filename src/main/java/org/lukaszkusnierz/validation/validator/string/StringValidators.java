package org.lukaszkusnierz.validation.validator.string;

import org.lukaszkusnierz.validation.chain.ValidationChainBuilder;
import org.lukaszkusnierz.validation.validator.NotNullValidator;

public class StringValidators extends ValidationChainBuilder<String> {

	public StringValidators notNull() {
		this.chain.add( new NotNullValidator<>() );
		return this;
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
}
