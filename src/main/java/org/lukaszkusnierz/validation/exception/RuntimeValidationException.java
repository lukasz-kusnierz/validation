package org.lukaszkusnierz.validation.exception;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

public class RuntimeValidationException extends RuntimeException {

	private final List<String> failureMessages;

	public RuntimeValidationException( final List<String> failureMessages ) {
		this.failureMessages = null == failureMessages ? Collections.<String>emptyList() : new ArrayList<>( failureMessages );
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
