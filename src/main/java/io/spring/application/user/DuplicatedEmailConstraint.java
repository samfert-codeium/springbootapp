package io.spring.application.user;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validation constraint annotation for checking duplicate email addresses.
 *
 * <p>This constraint ensures that an email address is not already registered
 * in the database. It's used during user registration to prevent duplicate accounts.
 *
 * @see DuplicatedEmailValidator
 */
@Constraint(validatedBy = DuplicatedEmailValidator.class)
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedEmailConstraint {
  /**
   * The error message to display when validation fails.
   *
   * @return the error message
   */
  String message() default "duplicated email";

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
