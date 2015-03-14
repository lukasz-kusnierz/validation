package org.lukaszkusnierz.validation.chain;

import org.junit.Test;
import org.lukaszkusnierz.validation.exception.CheckedValidationException;
import org.lukaszkusnierz.validation.exception.RuntimeValidationException;
import org.lukaszkusnierz.validation.result.Validated;

import java.io.IOException;

import static org.junit.Assert.*;

public class ValidationChainTest {

	@Test
	public void null_should_be_invalid() {
		//setup
		//examine
		final Validated<Object> result = ValidationChain
				.notNull()
				.validate( null );
		//verify
		assertFalse( result.isValid() );
		assertTrue( result.isInvalid() );
	}

	@Test(expected = CheckedValidationException.class)
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

	@Test(expected = RuntimeValidationException.class)
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

	@Test( expected = NumberFormatException.class )
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

	@Test
	public void validated_should_contain_failure_message() {
		//setup
		//examine
		final Validated<Object> validated = ValidationChain
				.notNull().otherwise( "Value cannot be null" )
				.validate( null );
		//verify
		assertEquals( 1, validated.getFailureMessages().size() );
		assertEquals( "Value cannot be null", validated.getFailureMessages().get( 0 ) );
	}

	@Test
	public void validated_should_contain_formatted_failure_message() {
		//setup
		//examine
		final Validated<Object> validated = ValidationChain
				.notNull().otherwise( "Value is %s", "invalid" )
				.validate( null );
		//verify
		assertEquals( 1, validated.getFailureMessages().size() );
		assertEquals( "Value is invalid", validated.getFailureMessages().get( 0 ) );
	}

	@Test
	public void validated_should_contain_custom_build_failure_message() {
		//setup
		//examine
		final Validated<Object> validated = ValidationChain
				.notNull().otherwise( ( value ) -> String.format( "Value '%s' cannot be null", value ) )
				.validate( null );
		//verify
		assertEquals( 1, validated.getFailureMessages().size() );
		assertEquals( "Value 'null' cannot be null", validated.getFailureMessages().get( 0 ) );
	}
}
