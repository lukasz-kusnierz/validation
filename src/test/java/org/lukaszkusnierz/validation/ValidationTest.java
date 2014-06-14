package org.lukaszkusnierz.validation;

import junit.framework.Assert;
import org.junit.Test;
import org.lukaszkusnierz.validation.validator.string.StringValidators;

public class ValidationTest {

	@Test
	public void complex_object_should_be_valid() {
		//setup
		final ComplexType complexType = new ComplexType( 4, "descritpion" );
		//examine
		ValidationResult<ComplexType> validationResult = Validation.of( ComplexType.class )
				.validate( ComplexType::getNumber ).with( i -> i > 3 )
				.validate( ComplexType::getText ).with( new StringValidators().exactLength( 11 ).chain() )
				.go( complexType );
		//verify
		Assert.assertTrue( validationResult.isValid() );
	}

	@Test
	public void complex_object_should_be_invalid() {
		//setup
		final ComplexType complexType = new ComplexType( 4, "descritpion" );
		//examine
		ValidationResult<ComplexType> validationResult = Validation.of( ComplexType.class )
				.validate( ComplexType::getNumber ).with( i -> i > 3 )
				.validate( ComplexType::getText ).with( new StringValidators().exactLength( 10 ).chain() )
				.go( complexType );
		//verify
		Assert.assertTrue( validationResult.isInvalid() );
	}

	private static class ComplexType {

		private final Integer number;
		private final String text;

		private ComplexType( final Integer number, final String text ) {
			this.number = number;
			this.text = text;
		}

		private Integer getNumber() {
			return number;
		}

		private String getText() {
			return text;
		}
	}
}
