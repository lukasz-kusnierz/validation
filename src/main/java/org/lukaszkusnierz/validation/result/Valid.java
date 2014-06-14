package org.lukaszkusnierz.validation.result;

import com.google.common.base.Objects;

public final class Valid<T> implements Validated<T> {

	private final T reference;

	public Valid( final T reference ) {
		this.reference = reference;
	}

	@Override
	public T get() {
		return this.reference;
	}

	@Override
	public boolean isValid() {
		return true;
	}

	@Override
	public boolean isInvalid() {
		return false;
	}

	@Override
	public T or( final T alternative ) {
		return this.reference;
	}

	@Override
	public int hashCode() {
		return Objects.hashCode( reference );
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null || getClass() != obj.getClass() ) {
			return false;
		}
		final Valid other = ( Valid ) obj;
		return Objects.equal( this.reference, other.reference );
	}

	@Override
	public String toString() {
		return Objects.toStringHelper( this )
				.add( "reference", reference )
				.toString();
	}
}
