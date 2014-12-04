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
