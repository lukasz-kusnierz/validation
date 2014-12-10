package org.lukaszkusnierz.validation.validator;

public final class NotNullValidator<T> implements Validator<T> {

	@Override
	public boolean isValid(final T subject) {
		return null != subject;
	}
}
