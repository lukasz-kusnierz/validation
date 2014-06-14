package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.function.Function;

public final class ValidationEntry<T, FIELD> {

	private final ValidationChain<FIELD> chain;
	private final Function<T, FIELD> fieldExtractor;
	private boolean breakOnFailure = false;

	public ValidationEntry( final ValidationChain<FIELD> chain, final Function<T, FIELD> fieldExtractor ) {
		if ( null == chain ) {
			throw new IllegalArgumentException( "Validation chain cannot be null" );
		}
		if ( null == fieldExtractor ) {
			throw new IllegalArgumentException( "Field extractor cannot be null, please use method reference syntax" );
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
