package org.lukaszkusnierz.validation.validator;

/**
 * Validator is a fundamental building block for validation process.
 *
 * @param <T> type of object under validation
 */
public interface Validator<T> {

	/**
	 * Checks if the subject is valid.
	 * Implementations should be null-safe and should not modify the argument.
	 *
	 * @param subject object under validation
	 * @return <code>true</code> if subject is valid, <code>false</code> otherwise
	 */
	boolean isValid(T subject);
}
