package org.lukaszkusnierz.validation.chain;

import java.util.Objects;

public abstract class ValidationChainBuilder<T> {

	private final ValidationChain<T> chain = ValidationChain.empty();

	public ValidationChain<T> chain() {
		return this.chain;
	}

	protected void _breakOnAnyFailure() {
		this.chain.breakOnAnyFailure();
	}

	protected void _breakOnFailure() {
		this.chain.breakOnFailure();
	}

	@Override
	public int hashCode() {
		return Objects.hash(chain);
	}

	@Override
	public boolean equals(final Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null || getClass() != obj.getClass()) {
			return false;
		}
		final ValidationChainBuilder other = (ValidationChainBuilder) obj;
		return Objects.equals(this.chain, other.chain);
	}
}