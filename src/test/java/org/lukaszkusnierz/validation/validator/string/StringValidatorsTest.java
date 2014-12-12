package org.lukaszkusnierz.validation.validator.string;

import org.junit.Test;
import org.lukaszkusnierz.validation.result.Validated;

import static org.junit.Assert.assertTrue;

public class StringValidatorsTest {

	@Test
	public void notBlank_just_whitespace_should_be_invalid() throws Exception {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.notBlank()
			.chain()
			.validate("   \t  \n ");
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void notBlank_null_should_be_invalid() throws Exception {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.notBlank()
			.chain()
			.validate(null);
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void notBlank_any_char_should_be_valid() throws Exception {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.notBlank()
			.chain()
			.validate("    t    \n");
		//verify
		assertTrue(validated.isValid());
	}

	@Test
	public void not_empty_string_should_be_valid() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.notEmpty()
			.chain()
			.validate("not empty");
		//verify
		assertTrue(validated.isValid());
	}

	@Test
	public void empty_string_should_be_invalid() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.notEmpty()
			.chain()
			.validate("");
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void null_string_should_be_invalid() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.notEmpty()
			.chain()
			.validate(null);
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void should_be_invalid_due_to_min_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.minLength(3)
			.chain()
			.validate("22");
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void should_be_valid_due_to_min_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.minLength(3)
			.chain()
			.validate("222");
		//verify
		assertTrue(validated.isValid());
	}

	@Test
	public void should_be_invalid_due_to_max_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.maxLength(3)
			.chain()
			.validate("2222");
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void should_be_valid_due_to_max_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.maxLength(3)
			.chain()
			.validate("222");
		//verify
		assertTrue(validated.isValid());
	}

	@Test
	public void should_be_invalid_due_to_exact_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.exactLength(4)
			.chain()
			.validate("222");
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void should_be_valid_due_to_exact_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.exactLength(4)
			.chain()
			.validate("2222");
		//verify
		assertTrue(validated.isValid());
	}

	@Test
	public void first_should_be_invalid_due_to_min_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.minLength(3)
			.maxLength(4)
			.chain()
			.validate("22");
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void second_should_be_invalid_due_to_max_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.minLength(3)
			.maxLength(4)
			.chain()
			.validate("22222");
		//verify
		assertTrue(validated.isInvalid());
	}

	@Test
	public void both_should_be_valid_due_to_correct_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
			.minLength(3)
			.maxLength(4)
			.chain()
			.validate("222");
		//verify
		assertTrue(validated.isValid());
	}
}
