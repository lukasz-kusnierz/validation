package org.lukaszkusnierz.validation.result;

import java.util.Objects;

public final class Valid<T> {

	private final T reference;

	public Valid(final T reference) {
		this.reference = reference;
	}

	public T get() {
		return reference;
	}

	@Override
	public int hashCode() {
		return Objects.hash(reference);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final Valid other = (Valid) obj;
		return Objects.equals(this.reference, other.reference);
	}

	@Override
	public String toString() {
		return null == reference ? null : reference.toString();
	}
}