package io.spring.application.user;

import io.spring.core.user.UserRepository;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Validator implementation for the {@link DuplicatedEmailConstraint}.
 *
 * <p>This validator checks if an email address is already registered in the database.
 * Null or empty values are considered valid (other constraints handle required fields).
 *
 * @see DuplicatedEmailConstraint
 */
public class DuplicatedEmailValidator
    implements ConstraintValidator<DuplicatedEmailConstraint, String> {

  /** Repository for querying existing users. */
  @Autowired private UserRepository userRepository;

  /**
   * Validates that the email address is not already registered.
   *
   * @param value the email address to validate
   * @param context the constraint validator context
   * @return {@code true} if the email is not registered or is empty, {@code false} otherwise
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return (value == null || value.isEmpty()) || !userRepository.findByEmail(value).isPresent();
  }
}
