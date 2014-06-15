package org.lukaszkusnierz.validation.result;

public interface Validated<T> {

	T get();

	boolean isValid();

	boolean isInvalid();

	T or( T alternative );

	T orNull();

	OrThrow<T> orThrow();

	boolean equalReference( Validated<T> another );

	boolean equalReference( T another );
}
