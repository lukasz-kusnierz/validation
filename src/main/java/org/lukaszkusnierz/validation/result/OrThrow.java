package org.lukaszkusnierz.validation.result;

import org.lukaszkusnierz.validation.exception.CheckedValidationException;
import org.lukaszkusnierz.validation.exception.RuntimeValidationException;

import java.util.function.Supplier;

/**
 * OrThrow let's you decide what kind of exception you want to raise on validation failure.
 *
 * @param <T> type of the object under validation
 */
public interface OrThrow<T> {

	/**
	 * @return object under validation, if valid
	 * @throws CheckedValidationException if the object is invalid
	 */
	Valid<T> checkedException() throws CheckedValidationException;

	/**
	 * @param useMethodReferenceSyntaxToReferenceConstructor supplier creating new checked exception in case of invalid object; use method reference syntax for readability, preferably, reference the constructor
	 * @param <EX> exception type
	 * @return object under validation, if valid
	 * @throws EX if the object is invalid
	 */
	<EX extends Exception> Valid<T> checkedException( Supplier<EX> useMethodReferenceSyntaxToReferenceConstructor ) throws EX;

	/**
	 * @return object under validation, if valid
	 * @throws RuntimeValidationException if the object is invalid
	 */
	Valid<T> runtimeException() throws RuntimeValidationException;

	/**
	 * @param useMethodReferenceSyntaxToReferenceConstructor supplier creating new runtime exception in case of invalid object; use method reference syntax for readability, preferably, reference the constructor
	 * @param <EX> exception type
	 * @return object under validation, if valid
	 * @throws EX if the object is invalid
	 */
	<EX extends RuntimeException> Valid<T> runtimeException( Supplier<EX> useMethodReferenceSyntaxToReferenceConstructor ) throws EX;
}
