package org.lukaszkusnierz.validation.validator.string;

import org.junit.Assert;
import org.junit.Test;
import org.lukaszkusnierz.validation.result.Validated;

public class StringValidatorsTest {

	@Test
	public void not_empty_string_should_be_valid() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.notEmpty()
				.chain()
				.validate( "not empty" );
		//verify
		Assert.assertTrue( validated.isValid() );
	}

	@Test
	public void empty_string_should_be_invalid() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.notEmpty()
				.chain()
				.validate( "" );
		//verify
		Assert.assertTrue( validated.isInvalid() );
	}

	@Test
	public void null_string_should_be_invalid() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.notEmpty()
				.chain()
				.validate( null );
		//verify
		Assert.assertTrue( validated.isInvalid() );
	}

	@Test
	public void should_be_invalid_due_to_min_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.minLength( 3 )
				.chain()
				.validate( "22" );
		//verify
		Assert.assertTrue( validated.isInvalid() );
	}

	@Test
	public void should_be_valid_due_to_min_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.minLength( 3 )
				.chain()
				.validate( "222" );
		//verify
		Assert.assertTrue( validated.isValid() );
	}

	@Test
	public void should_be_invalid_due_to_max_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.maxLength( 3 )
				.chain()
				.validate( "2222" );
		//verify
		Assert.assertTrue( validated.isInvalid() );
	}

	@Test
	public void should_be_valid_due_to_max_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.maxLength( 3 )
				.chain()
				.validate( "222" );
		//verify
		Assert.assertTrue( validated.isValid() );
	}

	@Test
	public void should_be_invalid_due_to_exact_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.exactLength( 4 )
				.chain()
				.validate( "222" );
		//verify
		Assert.assertTrue( validated.isInvalid() );
	}

	@Test
	public void should_be_valid_due_to_exact_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.exactLength( 4 )
				.chain()
				.validate( "2222" );
		//verify
		Assert.assertTrue( validated.isValid() );
	}

	@Test
	public void first_should_be_invalid_due_to_min_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.minLength( 3 )
				.maxLength( 4 )
				.chain()
				.validate( "22" );
		//verify
		Assert.assertTrue( validated.isInvalid() );
	}

	@Test
	public void second_should_be_invalid_due_to_max_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.minLength( 3 )
				.maxLength( 4 )
				.chain()
				.validate( "22222" );
		//verify
		Assert.assertTrue( validated.isInvalid() );
	}

	@Test
	public void both_should_be_valid_due_to_correct_length() {
		//setup
		//examine
		final Validated<String> validated = new StringValidators()
				.minLength( 3 )
				.maxLength( 4 )
				.chain()
				.validate( "222" );
		//verify
		Assert.assertTrue( validated.isValid() );
	}
}
