package io.spring.api.exception;

import org.springframework.validation.Errors;

/**
 * Exception thrown when a request contains validation errors.
 *
 * <p>This exception wraps Spring's {@link Errors} object containing
 * field-level validation errors. It results in an HTTP 422 response
 * with detailed error information.
 *
 * @see CustomizeExceptionHandler#handleInvalidRequest
 */
@SuppressWarnings("serial")
public class InvalidRequestException extends RuntimeException {
  /** The validation errors from the request. */
  private final Errors errors;

  /**
   * Creates a new InvalidRequestException with the specified validation errors.
   *
   * @param errors the validation errors
   */
  public InvalidRequestException(Errors errors) {
    super("");
    this.errors = errors;
  }

  /**
   * Returns the validation errors.
   *
   * @return the validation errors
   */
  public Errors getErrors() {
    return errors;
  }
}
