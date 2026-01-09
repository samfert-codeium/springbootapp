package io.spring.application.user;

import io.spring.core.user.User;
import io.spring.core.user.UserRepository;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Service class for user write operations.
 *
 * <p>This service handles user registration and profile updates, including
 * password encoding and validation of unique constraints for email and username.
 *
 * @see io.spring.application.UserQueryService
 * @see User
 */
@Service
@Validated
public class UserService {
  /** Repository for persisting user entities. */
  private UserRepository userRepository;

  /** Default profile image URL for new users. */
  private String defaultImage;

  /** Encoder for hashing user passwords. */
  private PasswordEncoder passwordEncoder;

  /**
   * Creates a new UserService with the required dependencies.
   *
   * @param userRepository the repository for user persistence
   * @param defaultImage the default profile image URL
   * @param passwordEncoder the password encoder for hashing passwords
   */
  @Autowired
  public UserService(
      UserRepository userRepository,
      @Value("${image.default}") String defaultImage,
      PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.defaultImage = defaultImage;
    this.passwordEncoder = passwordEncoder;
  }

  /**
   * Creates a new user account.
   *
   * <p>The password is encoded before storage, and the user is assigned
   * the default profile image.
   *
   * @param registerParam the registration parameters
   * @return the newly created user
   */
  public User createUser(@Valid RegisterParam registerParam) {
    User user =
        new User(
            registerParam.getEmail(),
            registerParam.getUsername(),
            passwordEncoder.encode(registerParam.getPassword()),
            "",
            defaultImage);
    userRepository.save(user);
    return user;
  }

  /**
   * Updates an existing user's profile.
   *
   * <p>Only non-empty fields in the update parameters will be applied.
   *
   * @param command the update command containing the target user and parameters
   */
  public void updateUser(@Valid UpdateUserCommand command) {
    User user = command.getTargetUser();
    UpdateUserParam updateUserParam = command.getParam();
    user.update(
        updateUserParam.getEmail(),
        updateUserParam.getUsername(),
        updateUserParam.getPassword(),
        updateUserParam.getBio(),
        updateUserParam.getImage());
    userRepository.save(user);
  }
}

/**
 * Validation constraint annotation for user update operations.
 *
 * <p>This constraint ensures that email and username updates don't conflict
 * with existing users (other than the user being updated).
 *
 * @see UpdateUserValidator
 */
@Constraint(validatedBy = UpdateUserValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@interface UpdateUserConstraint {
  /**
   * The error message to display when validation fails.
   *
   * @return the error message
   */
  String message() default "invalid update param";

  /**
   * Groups for constraint categorization.
   *
   * @return the validation groups
   */
  Class[] groups() default {};

  /**
   * Payload for extensibility purposes.
   *
   * @return the payload classes
   */
  Class[] payload() default {};
}

/**
 * Validator implementation for the {@link UpdateUserConstraint}.
 *
 * <p>This validator checks that email and username updates don't conflict
 * with other existing users. It allows the user to keep their current
 * email or username.
 *
 * @see UpdateUserConstraint
 */
class UpdateUserValidator implements ConstraintValidator<UpdateUserConstraint, UpdateUserCommand> {

  /** Repository for querying existing users. */
  @Autowired private UserRepository userRepository;

  /**
   * Validates that the email and username updates don't conflict with other users.
   *
   * @param value the update command to validate
   * @param context the constraint validator context
   * @return {@code true} if the update is valid, {@code false} otherwise
   */
  @Override
  public boolean isValid(UpdateUserCommand value, ConstraintValidatorContext context) {
    String inputEmail = value.getParam().getEmail();
    String inputUsername = value.getParam().getUsername();
    final User targetUser = value.getTargetUser();

    boolean isEmailValid =
        userRepository.findByEmail(inputEmail).map(user -> user.equals(targetUser)).orElse(true);
    boolean isUsernameValid =
        userRepository
            .findByUsername(inputUsername)
            .map(user -> user.equals(targetUser))
            .orElse(true);
    if (isEmailValid && isUsernameValid) {
      return true;
    } else {
      context.disableDefaultConstraintViolation();
      if (!isEmailValid) {
        context
            .buildConstraintViolationWithTemplate("email already exist")
            .addPropertyNode("email")
            .addConstraintViolation();
      }
      if (!isUsernameValid) {
        context
            .buildConstraintViolationWithTemplate("username already exist")
            .addPropertyNode("username")
            .addConstraintViolation();
      }
      return false;
    }
  }
}
