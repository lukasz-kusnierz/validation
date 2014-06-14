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
}
