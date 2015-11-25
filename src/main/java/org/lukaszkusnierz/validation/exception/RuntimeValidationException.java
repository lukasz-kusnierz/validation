package org.lukaszkusnierz.validation.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class RuntimeValidationException extends RuntimeException {

	private final List<String> failureMessages;

	public RuntimeValidationException( final String failureMessage, final Object... params ) {
		this( Collections.singletonList( String.format( failureMessage, params ) ) );
	}

	public RuntimeValidationException( final List<String> failureMessages ) {
		super(null == failureMessages ? null : failureMessages.stream().collect( Collectors.joining( "; " ) ) );
		this.failureMessages = null == failureMessages ? Collections.<String>emptyList() : new ArrayList<>( failureMessages );
	}

	public Optional<String> getAllFailureMessages() {
		return hasFailureMessages() ? Optional.of( this.failureMessages.stream().collect( Collectors.joining( "; " ) ) ) : Optional.empty();
	}

	public List<String> getFailureMessages() {
		return failureMessages;
	}

	public Optional<String> firstFailureMessage() {
		return this.failureMessages.isEmpty() ? Optional.<String>empty() : Optional.of( this.failureMessages.get( 0 ) );
	}

	public boolean hasFailureMessages() {
		return !this.failureMessages.isEmpty();
	}
}
