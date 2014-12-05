# Validation
==========

This utility library is intended to make your general-purpose validation a breeze. Java 8 only!

# Concepts
==========

## Validator

```Validator<T>``` is a fundamental building block. It has to say whether the argument is valid or not. Validators are not expected to throw an exception. Therefore, any exception thrown by the validator will be thrown up for you to handle, and will not be considered part of the validation process.

```java
public interface Validator<T> {
	boolean isValid( T subject );
}
```

## ValidationChain

```ValidationChain``` is used to combine multiple validators to perform a validation of **single** value.

```java
ValidationChain
		.notNull()
		.validate( "subject" )
		.orThrow()
		.checkedException( IOException::new )
```

## Validation

```Validation<T>``` is used to setup a validation of complex object, containing of multiple values being validated separately.

```java
SampleComplexType complexObject = new SampleComplexType( 4, "text", Arrays.asList( 20, 30, 18 ) );

SampleComplexType validComplexObjectOrNull = Validation.of( SampleComplexType.class )
		.validate( SampleComplexType::getText ).with( t -> t.length() > 2 )
		.validate( SampleComplexType::getAge ).with( a -> !a.isEmpty() )
		.validateAll( SampleComplexType::getAge ).with( age -> age > 18 )
		.go( complexObject )
		.orNull();
```

## Validated

Every validation operation (except invoking single ```Validator<T>``` directly) returns a ```Validated<T>``` object.
With ```Validated<T>``` object you can easily decide how you want to proceed.
Your options are:

First of all, you can just unwrap the original object when you are not interested if it is valid or not.

```java
T object = validatedObject.get();
```

You can check if the object is valid or not. There are two methods for better readability:

```java
boolean valid = validatedObject.isValid();
```

```java
boolean invalid = validatedObject.isInvalid();
```

You can check if the original object is equal to another one, you don't even need to unwrap another ```Validated<T>``` object.

```java
boolean equal = validatedObject.equalReference( T another );
```

```java
boolean equal = validatedObject.equalReference( Validated<T> another );
```

You can use Optional-style ```orNull()``` and ```or( T alternative )``` and think of valid object as a ```Present``` and invalid as an ```Absent``` instance:

```java
T object = validatedObject.orNull();
```

```java
T object = validatedObject.or( T alternative );
```

Next, you may want to raise any kind of exception in case of invalid data. Both checked an unchecked exceptions are supported. ```CheckedValidationException``` and ```RuntimeValidationException``` are provided for you, in case you don't need to create your own exceptions for this occasion:

```java
T object = validatedObject.orThrow().checkedException(); // throws CheckedValidationException
```

```java
T object = validatedObject.orThrow().checkedException( MyCheckedException::new ); // throws MyCheckedException
```

```java
T object = validatedObject.orThrow().runtimeException(); // throws RuntimeValidationException
```

```java
T object = validatedObject.orThrow().runtimeException( MyRuntimeException::new ); // throws MyRuntimeException
```
Finally, you can provide any custom exception supplier:

```java
T object = validatedObject.orThrow().checkedException( () -> {
			String message = "ooops";
			return new MyCheckedException( message );
		}); // throws MyCheckedException
```

### Discussion
You may ask, why the following construct is not supported, and why you must provide exception supplier either directly, or using method reference syntax:

```java
T object = validatedObject.orThrow().runtimeException( new MyRuntimeException() ); // throws MyRuntimeException
```

This way exception object would be created every time, even if the validation turns out to be successful.
Creating exceptions in Java is significantly more expensive than creating a regular object, bacause it involves filling in the stack trace. You should avoid creating unnecessary exceptions.
