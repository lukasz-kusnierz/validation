package org.lukaszkusnierz.validation.chain;

import org.junit.Assert;
import org.junit.Test;
import org.lukaszkusnierz.validation.exception.CheckedValidationException;
import org.lukaszkusnierz.validation.exception.RuntimeValidationException;
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

	@Test( expected = CheckedValidationException.class )
	public void null_should_be_invalid_and_throw_checked_exception() throws CheckedValidationException {
		//setup
		//examine
		ValidationChain
				.notNull()
				.validate( null )
				.orThrow()
				.checkedException();
		//verify
	}

	@Test( expected = IOException.class )
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

	@Test( expected = RuntimeValidationException.class )
	public void null_should_be_invalid_and_throw_runtime_exception() {
		//setup
		//examine
		ValidationChain
				.notNull()
				.validate( null )
				.orThrow()
				.runtimeException();
		//verify
	}

	@Test(expected = NumberFormatException.class)
	public void null_should_be_invalid_and_throw_custom_runtime_exception() {
		//setup
		//examine
		ValidationChain
				.notNull()
				.validate( null )
				.orThrow()
				.runtimeException( NumberFormatException::new );
		//verify
	}
}
