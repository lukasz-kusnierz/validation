package org.lukaszkusnierz.validation.chain;

import org.junit.Assert;
import org.junit.Test;
import org.lukaszkusnierz.validation.result.Validated;

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
	}
}
