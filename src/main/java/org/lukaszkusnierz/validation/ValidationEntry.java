package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.result.Validated;

public interface ValidationEntry<T, FIELD> {

	Validated<FIELD> validate( T subject );

	void breakOnFailure();

	boolean isBreakOnFailure();
}
