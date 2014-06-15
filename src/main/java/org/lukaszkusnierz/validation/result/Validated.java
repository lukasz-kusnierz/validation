package org.lukaszkusnierz.validation.result;

public interface Validated<T> {

	T get();

	boolean isValid();

	boolean isInvalid();

	T or( T alternative );

	OrThrow<T> orThrow();
}
