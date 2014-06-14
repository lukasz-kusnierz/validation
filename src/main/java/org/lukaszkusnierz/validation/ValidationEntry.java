package org.lukaszkusnierz.validation;

import com.google.common.base.Preconditions;
import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.function.Function;

public final class ValidationEntry<T, FIELD> {

	private final ValidationChain<FIELD> chain;
	private final Function<T, FIELD> fieldExtractor;
	private boolean breakOnFailure = false;

	public ValidationEntry( final ValidationChain<FIELD> chain, final Function<T, FIELD> fieldExtractor ) {
		Preconditions.checkArgument( null != chain, "Validation chain cannot be null" );
		Preconditions.checkArgument( null != fieldExtractor, "Field extractor cannot be null, please use method reference syntax" );
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
