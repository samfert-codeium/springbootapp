package io.spring.application.user;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validation constraint annotation for checking duplicate usernames.
 *
 * <p>This constraint ensures that a username is not already taken
 * in the database. It's used during user registration to prevent duplicate usernames.
 *
 * @see DuplicatedUsernameValidator
 */
@Constraint(validatedBy = DuplicatedUsernameValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@interface DuplicatedUsernameConstraint {
  /**
   * The error message to display when validation fails.
   *
   * @return the error message
   */
  String message() default "duplicated username";

  /**
   * Groups for constraint categorization.
   *
   * @return the validation groups
   */
  Class<?>[] groups() default {};

  /**
   * Payload for extensibility purposes.
   *
   * @return the payload classes
   */
  Class<? extends Payload>[] payload() default {};
}
