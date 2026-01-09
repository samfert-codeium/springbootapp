package io.spring.application.user;

import io.spring.core.user.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Validator implementation for the {@link DuplicatedUsernameConstraint}.
 *
 * <p>This validator checks if a username is already taken in the database.
 * Null or empty values are considered valid (other constraints handle required fields).
 *
 * @see DuplicatedUsernameConstraint
 */
class DuplicatedUsernameValidator
    implements ConstraintValidator<DuplicatedUsernameConstraint, String> {

  /** Repository for querying existing users. */
  @Autowired private UserRepository userRepository;

  /**
   * Validates that the username is not already taken.
   *
   * @param value the username to validate
   * @param context the constraint validator context
   * @return {@code true} if the username is not taken or is empty, {@code false} otherwise
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return (value == null || value.isEmpty()) || !userRepository.findByUsername(value).isPresent();
  }
}
