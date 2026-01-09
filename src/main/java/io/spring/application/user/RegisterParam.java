package io.spring.application.user;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Parameter object for user registration.
 *
 * <p>This class represents the input data required to register a new user,
 * including validation constraints to ensure data integrity and uniqueness.
 *
 * @see UserService#createUser(RegisterParam)
 */
@Getter
@JsonRootName("user")
@AllArgsConstructor
@NoArgsConstructor
public class RegisterParam {
  /**
   * The user's email address.
   * Must be a valid email format and not already registered.
   */
  @NotBlank(message = "can't be empty")
  @Email(message = "should be an email")
  @DuplicatedEmailConstraint
  private String email;

  /**
   * The user's chosen username.
   * Must not be blank and not already taken.
   */
  @NotBlank(message = "can't be empty")
  @DuplicatedUsernameConstraint
  private String username;

  /** The user's password. Must not be blank. */
  @NotBlank(message = "can't be empty")
  private String password;
}
