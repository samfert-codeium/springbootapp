package io.spring.api.exception;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonRootName;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import java.util.List;

/**
 * Resource class representing API error responses.
 *
 * <p>This class wraps a collection of field errors for serialization
 * in API error responses. It uses a custom serializer to format the
 * errors according to the RealWorld API specification.
 *
 * @see ErrorResourceSerializer
 * @see FieldErrorResource
 */
@JsonSerialize(using = ErrorResourceSerializer.class)
@JsonIgnoreProperties(ignoreUnknown = true)
@lombok.Getter
@JsonRootName("errors")
public class ErrorResource {
  /** The list of field-level errors. */
  private List<FieldErrorResource> fieldErrors;

  /**
   * Creates a new ErrorResource with the specified field errors.
   *
   * @param fieldErrorResources the list of field errors
   */
  public ErrorResource(List<FieldErrorResource> fieldErrorResources) {
    this.fieldErrors = fieldErrorResources;
  }
}
