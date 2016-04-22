package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.chain.ValidationChainBuilder;
import org.lukaszkusnierz.validation.validator.NotNullValidator;
import org.lukaszkusnierz.validation.validator.Validator;

import java.util.function.Function;

public final class ValidationEntryBuilder<T, FIELD> {

	private final Validation<T> validation;
	private final Function<T, FIELD> fieldExtractor;

	public ValidationEntryBuilder(final Validation<T> validation, final Function<T, FIELD> fieldExtractor) {
		if (null == validation) {
			throw new IllegalArgumentException("Validation cannot be null, can it?");
		}
		if (null == fieldExtractor) {
			throw new IllegalArgumentException("Field extractor cannot be null, use method reference syntax, ex: with( String::length )");
		}
		this.validation = validation;
		this.fieldExtractor = fieldExtractor;
	}

	public Validation<T> with(final ValidationChain<FIELD> validationChain) {
		return this.validation.add(new SimpleValidationEntry<>(validationChain, this.fieldExtractor));
	}

	public Validation<T> with(final Validator<FIELD> validator) {
		return this.validation.add(new SimpleValidationEntry<>(ValidationChain.use(validator), this.fieldExtractor));
	}

	public Validation<T> with(final ValidationChainBuilder<FIELD> validationChainBuilder) {
		if (null == validationChainBuilder) {
			throw new IllegalArgumentException("ValidationChainBuilder cannot be null");
		}
		return this.validation.add(new SimpleValidationEntry<>(validationChainBuilder.chain(), this.fieldExtractor));
	}

	public Validation<T> notNull() {
		return with( new NotNullValidator<>() );
	}
}
