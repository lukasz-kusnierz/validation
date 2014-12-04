# Validation
==========

This utility library is intended to make your general-purpose validation a breeze. Java 8 only!

# Concepts
==========

## Validator

```java
public interface Validator<T> {
	boolean isValid( T subject );
}
```

## ValidationChain

```java
ValidationChain
		.notNull()
		.validate( "subject" )
		.orThrow()
		.checkedException( IOException::new )
```

## Validation

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

Every validation operation (except invoking single Validator directly) returns a Validated<T> object.
With Validated<T> object you can easily decide how you want to proceed.
Your options are:

```java
T object = validatedObject.orNull();
```

```java
T object = validatedObject.or( T alternative );
```

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
