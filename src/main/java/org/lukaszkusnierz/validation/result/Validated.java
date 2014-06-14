package org.lukaszkusnierz.validation.result;

public interface Validated<T> {

	T get();

	boolean isValid();

	T or( T alternative );
}
