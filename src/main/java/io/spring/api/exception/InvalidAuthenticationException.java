package io.spring.api.exception;

/**
 * Exception thrown when user authentication fails.
 *
 * <p>This exception is thrown when a user provides invalid credentials
 * (email or password) during login. It results in an HTTP 422 response.
 *
 * @see CustomizeExceptionHandler#handleInvalidAuthentication
 */
public class InvalidAuthenticationException extends RuntimeException {

  /**
   * Creates a new InvalidAuthenticationException with a default message.
   */
  public InvalidAuthenticationException() {
    super("invalid email or password");
  }
}
