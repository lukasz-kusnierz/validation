package org.lukaszkusnierz.validation.result;

import org.lukaszkusnierz.validation.exception.ValidationException;

import java.util.function.Supplier;

public interface OrThrow<T> {

	T checkedException() throws ValidationException;

	<EX extends Exception> T checkedException( Supplier<EX> exceptionSupplier ) throws EX;
}
