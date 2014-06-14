package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.function.Function;

public class ValidationEntry<T, FIELD> {

	private final ValidationChain<FIELD> chain;
	private final Function<T, FIELD> fieldExtractor;
	private boolean breakOnFailure = false;

	public ValidationEntry( final ValidationChain<FIELD> chain, final Function<T, FIELD> fieldExtractor ) {
		this.chain = chain;
		this.fieldExtractor = fieldExtractor;
	}

	public Validated<FIELD> validate( final T subject ) {
		final FIELD fieldValue = this.fieldExtractor.apply( subject );
		return this.chain.validate( fieldValue );
	}

	public void breakOnFailure() {
		this.breakOnFailure = true;
	}

	public boolean isBreakOnFailure() {
		return breakOnFailure;
	}
}
