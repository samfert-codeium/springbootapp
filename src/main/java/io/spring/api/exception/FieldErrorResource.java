package io.spring.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Resource class representing a single field validation error.
 *
 * <p>This class contains details about a validation error on a specific field,
 * including the resource name, field name, error code, and human-readable message.
 *
 * @see ErrorResource
 */
@JsonIgnoreProperties(ignoreUnknown = true)
@Getter
@AllArgsConstructor
public class FieldErrorResource {
  /** The name of the resource/object containing the field. */
  private String resource;

  /** The name of the field with the error. */
  private String field;

  /** The validation error code. */
  private String code;

  /** The human-readable error message. */
  private String message;
}
