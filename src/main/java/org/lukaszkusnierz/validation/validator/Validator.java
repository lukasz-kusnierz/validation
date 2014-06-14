package org.lukaszkusnierz.validation.validator;

public interface Validator<T> {

	boolean isValid( T subject );
}
