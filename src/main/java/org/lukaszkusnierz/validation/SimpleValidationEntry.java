package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.function.Function;

public final class SimpleValidationEntry<T, FIELD> {

	private final ValidationChain<FIELD> chain;
	private final Function<T, FIELD> fieldExtractor;
	private boolean breakOnFailure = false;

	public SimpleValidationEntry( final ValidationChain<FIELD> chain, final Function<T, FIELD> fieldExtractor ) {
		if ( null == chain ) {
			throw new IllegalArgumentException( "Validation chain just cannot be null, you have no excuse" );
		}
		if ( null == fieldExtractor ) {
			throw new IllegalArgumentException( "Field extractor cannot be null, use method reference syntax, ex: with( String::length )" );
		}
		this.chain = chain;
		this.fieldExtractor = fieldExtractor;
	}

	public Validated<FIELD> validate( final T subject ) {
		return this.chain.validate( this.fieldExtractor.apply( subject ) );
	}

	public void breakOnFailure() {
		this.breakOnFailure = true;
	}

	public boolean isBreakOnFailure() {
		return breakOnFailure;
	}
}
