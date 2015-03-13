package org.lukaszkusnierz.validation.validator;

import java.util.function.Function;

/**
 * Validator is a fundamental building block for validation process.
 *
 * @param <T> type of object under validation
 */
public interface Validator<T> {

	public static <T> Validator<T> fromFunction( final Function<T, Boolean> function ) {
		if ( null == function ) {
			throw new IllegalArgumentException( "Function cannot be null" );
		}
		return ( subject ) -> {
			final Boolean isValid = function.apply( subject );
			if ( null == isValid ) {
				throw new NullPointerException( String.format( "Your function '%s' did return null for input: '%s'. It must always return a Boolean to use it as a Validator.", function, subject ) );
			}
			return isValid;
		};
	}

	/**
	 * Checks if the subject is valid.
	 * Implementations should be null-safe and should not modify the argument.
	 *
	 * @param subject object under validation
	 * @return <code>true</code> if subject is valid, <code>false</code> otherwise
	 */
	boolean isValid( T subject );
}
