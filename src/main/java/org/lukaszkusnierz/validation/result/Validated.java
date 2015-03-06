package org.lukaszkusnierz.validation.result;

import java.util.function.Function;
import java.util.function.Supplier;

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
	 * Allows to use alternate object provided by the supplier in case of invalid one
	 *
	 * @param supplier supplier used to create alternate object in case of failed validation
	 * @return object under validation if the validation was successful, or an object provided by the supplier
	 */
	T or( Supplier<T> supplier );

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

	<TARGET> Validated<TARGET> map( Function<T, TARGET> mapper );

	boolean equalReference( Validated<T> another );

	boolean equalReference( T another );
}
