package org.lukaszkusnierz.validation.chain;

import org.junit.Assert;
import org.junit.Test;
import org.lukaszkusnierz.validation.exception.ValidationException;
import org.lukaszkusnierz.validation.result.Validated;

import java.io.IOException;

public class ValidationChainTest {

	@Test
	public void null_should_be_invalid() {
		//setup
		//examine
		final Validated<Object> result = ValidationChain
				.notNull()
				.validate( null );
		//verify
		Assert.assertFalse( result.isValid() );
		Assert.assertTrue( result.isInvalid() );
	}

	@Test( expected = ValidationException.class )
	public void null_should_be_invalid_and_throw_checked_exception() throws ValidationException {
		//setup
		//examine
		ValidationChain
				.notNull()
				.validate( null )
				.orThrow()
				.checkedException();
		//verify
	}

	@Test(expected = IOException.class)
	public void null_should_be_invalid_and_throw_custom_checked_exception() throws IOException {
		//setup
		//examine
		ValidationChain
				.notNull()
				.validate( null )
				.orThrow()
				.checkedException( IOException::new );
		//verify
	}
}
