package io.spring.api.exception;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 * Exception thrown when a user lacks authorization for an operation.
 *
 * <p>This exception is thrown when a user attempts to perform an action
 * they are not authorized to do (e.g., editing another user's article).
 * It results in an HTTP 403 Forbidden response.
 */
@ResponseStatus(HttpStatus.FORBIDDEN)
public class NoAuthorizationException extends RuntimeException {}
