package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.validator.Validator;

import java.util.function.Function;

public final class ValidationEntryBuilder<T, FIELD> {

	private final Validation<T> validation;
	private final Function<T, FIELD> fieldExtractor;

	public ValidationEntryBuilder( final Validation<T> validation, final Function<T, FIELD> fieldExtractor ) {
		if ( null == validation ) {
			throw new IllegalArgumentException( "Validation cannot be null" );
		}
		if ( null == fieldExtractor ) {
			throw new IllegalArgumentException( "Field extractor cannot be null, please use method reference syntax" );
		}
		this.validation = validation;
		this.fieldExtractor = fieldExtractor;
	}

	public Validation<T> with( final ValidationChain<FIELD> validationChain ) {
		return this.validation.add( new ValidationEntry<T, FIELD>( validationChain, this.fieldExtractor ) );
	}

	public Validation<T> with( final Validator<FIELD> validator ) {
		return this.validation.add( new ValidationEntry<T, FIELD>( new ValidationChain<FIELD>().add( validator ), this.fieldExtractor ) );
	}
}
