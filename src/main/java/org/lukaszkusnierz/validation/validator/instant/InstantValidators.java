package org.lukaszkusnierz.validation.validator.instant;

import org.lukaszkusnierz.validation.chain.ValidationChainBuilder;
import org.lukaszkusnierz.validation.validator.NotNullValidator;

import java.time.Clock;
import java.time.Instant;

public class InstantValidators extends ValidationChainBuilder<Instant> {

	public InstantValidators notNull() {
		chain().add( new NotNullValidator<>() );
		return this;
	}

	public InstantValidators isPast( final Clock clock ) {
		if ( null == clock ) {
			throw new IllegalArgumentException( "Clock cannot be null" );
		}
		chain().add( ( instant ) -> null == instant ? false : clock.instant().isAfter( instant ) );
		return this;
	}

	public InstantValidators isFuture( final Clock clock ) {
		if ( null == clock ) {
			throw new IllegalArgumentException( "Clock cannot be null" );
		}
		chain().add( ( instant ) -> null == instant ? false : clock.instant().isBefore( instant ) );
		return this;
	}

	public InstantValidators isBefore( final Instant instant ) {
		chain().add( ( subject ) -> null == subject ? false : subject.isBefore( instant ) );
		return this;
	}

	public InstantValidators isAfter( final Instant instant ) {
		chain().add( ( subject ) -> null == subject ? false : subject.isAfter( instant ) );
		return this;
	}
}
