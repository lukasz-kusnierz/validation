package org.lukaszkusnierz.validation.validator;

public final class NotNullValidator<T> implements Validator<T> {

	private static final NotNullValidator INSTANCE = new NotNullValidator<>();

	private NotNullValidator() {
	}

	public static final <T> NotNullValidator<T> instance() {
		return INSTANCE;
	}

	@Override
	public boolean isValid( final T subject ) {
		return null != subject;
	}
}
