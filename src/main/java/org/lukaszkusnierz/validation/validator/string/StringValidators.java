package org.lukaszkusnierz.validation.validator.string;

import org.lukaszkusnierz.validation.chain.ValidationChainBuilder;
import org.lukaszkusnierz.validation.validator.NotNullValidator;

public class StringValidators extends ValidationChainBuilder<String> {

	public StringValidators notBlank() {
		chain().add(new NotBlankValidator());
		return this;
	}

	public StringValidators notNull() {
		chain().add(new NotNullValidator<>());
		return this;
	}

	public StringValidators notEmpty() {
		return this.minLength(1);
	}

	public StringValidators minLength(final int minLength) {
		chain().add(subject -> null != subject && subject.length() >= minLength);
		return this;
	}

	public StringValidators maxLength(final int maxLength) {
		chain().add(subject -> null != subject && subject.length() <= maxLength);
		return this;
	}

	public StringValidators exactLength(final int length) {
		chain().add(subject -> null != subject && subject.length() == length);
		return this;
	}
}
