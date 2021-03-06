package org.lukaszkusnierz.validation.validator.string;

import org.lukaszkusnierz.validation.validator.Validator;

public class NotBlankValidator implements Validator<String> {

	@Override
	public boolean isValid(final String subject) {
		if (null == subject) {
			return false;
		}
		final int length = subject.length();
		for (int i = 0; i < length; i++) {
			if (!Character.isWhitespace(subject.charAt(i))) {
				return true;
			}
		}
		return false;
	}
}
