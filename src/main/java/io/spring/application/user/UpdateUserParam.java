package io.spring.application.user;

import com.fasterxml.jackson.annotation.JsonRootName;
import javax.validation.constraints.Email;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Parameter object for updating user profile information.
 *
 * <p>This class represents the input data for updating a user's profile.
 * All fields are optional - only non-empty values will be applied.
 *
 * @see UserService#updateUser(UpdateUserCommand)
 * @see UpdateUserCommand
 */
@Getter
@JsonRootName("user")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UpdateUserParam {

  /** The new email address. Empty string means no change. */
  @Builder.Default
  @Email(message = "should be an email")
  private String email = "";

  /** The new password. Empty string means no change. */
  @Builder.Default private String password = "";

  /** The new username. Empty string means no change. */
  @Builder.Default private String username = "";

  /** The new biography. Empty string means no change. */
  @Builder.Default private String bio = "";

  /** The new profile image URL. Empty string means no change. */
  @Builder.Default private String image = "";
}
