package org.lukaszkusnierz.validation.result;

import java.util.Objects;

public final class Invalid<T> implements Validated<T> {

	private final T reference;

	public Invalid( final T reference ) {
		this.reference = reference;
	}

	@Override
	public T get() {
		return this.reference;
	}

	@Override
	public boolean isValid() {
		return false;
	}

	@Override
	public boolean isInvalid() {
		return true;
	}

	@Override
	public T or( final T alternative ) {
		return alternative;
	}

	@Override
	public int hashCode() {
		return Objects.hash( reference );
	}

	@Override
	public boolean equals( final Object obj ) {
		if ( this == obj ) {
			return true;
		}
		if ( obj == null || getClass() != obj.getClass() ) {
			return false;
		}
		final Invalid other = ( Invalid ) obj;
		return Objects.equals( this.reference, other.reference );
	}
}
