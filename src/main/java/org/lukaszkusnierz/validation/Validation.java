package org.lukaszkusnierz.validation;

import java.util.LinkedList;
import java.util.List;
import java.util.function.Function;

public class Validation<T> {

	private List<ValidationEntry<T, ?>> entries = new LinkedList<>();

	public static <T> Validation<T> of( final Class<T> c ) {
		return new Validation<T>();
	}

	public <FIELD> ValidationEntryBuilder<T, FIELD> validate( final Function<T, FIELD> useMethodReferenceSyntax ) {
		return new ValidationEntryBuilder( this, useMethodReferenceSyntax );
	}

	public Validation<T> add( final ValidationEntry<T, ?> entry ) {
		this.entries.add( entry );
		return this;
	}

	public void go( final T subject ) {
		for ( final ValidationEntry<T, ?> entry : this.entries ) {
			entry.validate( subject );
		}
	}
}
