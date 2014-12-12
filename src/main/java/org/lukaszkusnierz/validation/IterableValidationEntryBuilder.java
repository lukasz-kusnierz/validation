package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.chain.ValidationChain;
import org.lukaszkusnierz.validation.chain.ValidationChainBuilder;
import org.lukaszkusnierz.validation.validator.Validator;

import java.util.function.Function;

public final class IterableValidationEntryBuilder<T, FIELD, ITERABLE extends Iterable<FIELD>> {

	private final Validation<T> validation;
	private final Function<T, ITERABLE> fieldExtractor;

	public IterableValidationEntryBuilder(final Validation<T> validation, final Function<T, ITERABLE> fieldExtractor) {
		if (null == validation) {
			throw new IllegalArgumentException("Validation cannot be null, can it?");
		}
		if (null == fieldExtractor) {
			throw new IllegalArgumentException("Field extractor cannot be null, use method reference syntax, ex: with( Map::keySet )");
		}
		this.validation = validation;
		this.fieldExtractor = fieldExtractor;
	}

	public Validation<T> with(final ValidationChain<FIELD> validationChain) {
		return this.validation.add(new IterableValidationEntry<>(validationChain, this.fieldExtractor));
	}

	public Validation<T> with(final Validator<FIELD> validator) {
		return this.validation.add(new IterableValidationEntry<>(ValidationChain.use(validator), this.fieldExtractor));
	}

	public Validation<T> with(final ValidationChainBuilder<FIELD> validationChainBuilder) {
		if (null == validationChainBuilder) {
			throw new IllegalArgumentException("ValidationChainBuilder cannot be null");
		}
		return this.validation.add(new IterableValidationEntry<>(validationChainBuilder.chain(), this.fieldExtractor));
	}
}
