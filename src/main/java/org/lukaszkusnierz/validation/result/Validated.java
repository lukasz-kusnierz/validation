package org.lukaszkusnierz.validation.result;

public interface Validated<T> {

	/**
	 * Returns the object under validation, which may be valid or invalid
	 *
	 * @return object under validation, may be valid or invalid
	 */
	T get();

	/**
	 * @return <code>true</code> if validation was successful, <code>false</code> otherwise
	 */
	boolean isValid();

	/**
	 * @return <code>true</code> if validation was unsuccessful, <code>false</code> otherwise
	 */
	boolean isInvalid();

	/**
	 * Allows to use alternate object in case of invalid one
	 *
	 * @param alternative object to return in case of failed validation
	 * @return object under validation if the validation was successful, <code>alternative</code> otherwise
	 */
	T or( T alternative );

	/**
	 * @return object under validation if the validation was successful, <code>null</code> otherwise
	 */
	T orNull();

	/**
	 * Allows to throw an exception in case of failed validation
	 *
	 * @return exception builder
	 */
	OrThrow<T> orThrow();

	boolean equalReference( Validated<T> another );

	boolean equalReference( T another );
}
