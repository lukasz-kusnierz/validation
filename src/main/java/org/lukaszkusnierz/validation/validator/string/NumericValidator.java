package org.lukaszkusnierz.validation.validator.string;

import org.lukaszkusnierz.validation.validator.Validator;

public class NumericValidator implements Validator<String> {

	@Override
	public boolean isValid(final String subject) {
		if (null == subject) {
			return false;
		}
		final int length = subject.length();
		for (int i = 0; i < length; i++) {
			if (!Character.isDigit(subject.charAt(i))) {
				return false;
			}
		}
		return true;
	}
}
