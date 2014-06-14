package org.lukaszkusnierz.validation.validator.string;

import org.lukaszkusnierz.validation.validator.Validator;

public final class NotEmptyValidator implements Validator<String> {

	@Override
	public boolean isValid( final String subject ) {
		return subject != null && subject.length() > 0;
	}
}
