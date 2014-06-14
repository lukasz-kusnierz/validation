package org.lukaszkusnierz.validation;

import org.lukaszkusnierz.validation.result.Invalid;
import org.lukaszkusnierz.validation.result.Valid;
import org.lukaszkusnierz.validation.result.Validated;

import java.util.LinkedList;
import java.util.List;

public final class ValidationResult<T> {

	private final List<Valid<?>> valid = new LinkedList<>();
	private final List<Invalid<?>> invalid = new LinkedList<>();

	public void add( final Validated<?> validated ) {
		if ( validated instanceof Valid ) {
			this.valid.add( ( Valid<?> ) validated );
			return;
		}
		if ( validated instanceof Invalid ) {
			this.invalid.add( ( Invalid<?> ) validated );
			return;
		}
		throw new IllegalArgumentException( "Validated object must be either Valid or Invalid instance" );
	}

	public boolean isValid() {
		return this.invalid.isEmpty();
	}

	public boolean isInvalid() {
		return !this.isValid();
	}
}