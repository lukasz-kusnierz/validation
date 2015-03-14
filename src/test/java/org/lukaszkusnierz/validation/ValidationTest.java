package org.lukaszkusnierz.validation;

import org.junit.Test;
import org.lukaszkusnierz.validation.result.Validated;
import org.lukaszkusnierz.validation.validator.string.StringValidators;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.assertTrue;

public class ValidationTest {

	@Test
	public void complex_object_should_be_valid() {
		//setup
		final ComplexType complexType = new ComplexType( 4, "description", null );
		//examine
		Validated<ComplexType> validationResult = Validation.of( ComplexType.class )
				.validate( ComplexType::getNumber ).with( i -> i > 3 )
				.validate( ComplexType::getText ).with( new StringValidators().exactLength( 11 ).chain() )
				.go( complexType );
		//verify
		assertTrue( validationResult.isValid() );
	}

	@Test
	public void complex_object_should_be_invalid() {
		//setup
		final ComplexType complexType = new ComplexType( 4, "description", null );
		//examine
		Validated<ComplexType> validationResult = Validation.of( ComplexType.class )
				.validate( ComplexType::getNumber ).with( i -> i > 3 )
				.validate( ComplexType::getText ).with( new StringValidators().exactLength( 10 ).chain() )
				.go( complexType );
		//verify
		assertTrue( validationResult.isInvalid() );
	}

	@Test
	public void null_ages_should_be_valid() {
		//setup
		final ComplexType complexType = new ComplexType( 4, "description", null );
		//examine
		Validated<ComplexType> validationResult = Validation.of( ComplexType.class )
				.validateAll( ComplexType::getAge ).with( i -> i > 18 )
				.go( complexType );
		//verify
		assertTrue( validationResult.isValid() );
	}

	@Test
	public void all_ages_should_be_valid() {
		//setup
		final ComplexType complexType = new ComplexType( 4, "description", Arrays.asList( 20, 30, 40 ) );
		//examine
		Validated<ComplexType> validationResult = Validation.of( ComplexType.class )
				.validateAll( ComplexType::getAge ).with( i -> i > 18 )
				.go( complexType );
		//verify
		assertTrue( validationResult.isValid() );
	}

	@Test
	public void should_be_invalid_due_to_last_age() {
		//setup
		final ComplexType complexType = new ComplexType( 4, "description", Arrays.asList( 20, 30, 18 ) );
		//examine
		Validated<ComplexType> validationResult = Validation.of( ComplexType.class )
				.validateAll( ComplexType::getAge ).with( i -> i > 18 )
				.go( complexType );
		//verify
		assertTrue( validationResult.isInvalid() );
	}

	private static class ComplexType {

		private final Integer number;
		private final String text;
		private final List<Integer> age;

		private ComplexType( final Integer number, final String text, final List<Integer> age ) {
			this.number = number;
			this.text = text;
			this.age = age;
		}

		private Integer getNumber() {
			return number;
		}

		private String getText() {
			return text;
		}

		public List<Integer> getAge() {
			return age;
		}
	}
}
