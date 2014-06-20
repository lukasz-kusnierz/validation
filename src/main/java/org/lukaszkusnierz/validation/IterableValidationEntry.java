package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.result.Invalid;
import org.lukaszkusnierz.validation.result.Valid;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.function.Function;

public final class IterableValidationEntry<T, ITERABLE extends Iterable<FIELD>, FIELD> implements ValidationEntry<T, ITERABLE> {

	private final ValidationChain<FIELD> chain;
	private final Function<T, ITERABLE> fieldExtractor;
	private boolean breakOnFailure = false;

	public IterableValidationEntry( final ValidationChain<FIELD> chain, final Function<T, ITERABLE> fieldExtractor ) {
		if ( null == chain ) {
			throw new IllegalArgumentException( "Validation chain just cannot be null, you have no excuse" );
		}
		if ( null == fieldExtractor ) {
			throw new IllegalArgumentException( "Field extractor cannot be null, use method reference syntax, ex: with( Map::keySet )" );
		}
		this.chain = chain;
		this.fieldExtractor = fieldExtractor;
	}

	@Override
	public Validated<ITERABLE> validate( final T subject ) {
		final ITERABLE iterable = this.fieldExtractor.apply( subject );
		if ( null == iterable ) {
			return new Valid<>( iterable );
		}
		for ( final FIELD element : iterable ) {
			if( this.chain.validate( element ).isInvalid() ) {
				return new Invalid<>( iterable );
			}
		}
		return new Valid<>( iterable );
	}

	@Override
	public void breakOnFailure() {
		this.breakOnFailure = true;
	}

	@Override
	public boolean isBreakOnFailure() {
		return breakOnFailure;
	}
}
