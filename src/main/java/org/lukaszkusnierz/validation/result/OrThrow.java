package org.lukaszkusnierz.validation.result;

import org.lukaszkusnierz.validation.exception.CheckedValidationException;
import org.lukaszkusnierz.validation.exception.RuntimeValidationException;

import java.util.function.Supplier;

public interface OrThrow<T> {

	T checkedException() throws CheckedValidationException;

	<EX extends Exception> T checkedException( Supplier<EX> useMethodReferenceSyntaxToReferenceConstructor ) throws EX;

	T runtimeException() throws RuntimeValidationException;

	<EX extends RuntimeException> T runtimeException( Supplier<EX> useMethodReferenceSyntaxToReferenceConstructor ) throws EX;
}
