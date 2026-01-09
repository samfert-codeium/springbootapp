package io.spring.application.user;

import io.spring.core.user.User;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Command object for updating a user's profile.
 *
 * <p>This class combines the target user with the update parameters,
 * allowing validation to check for conflicts with other users.
 *
 * @see UserService#updateUser(UpdateUserCommand)
 * @see UpdateUserParam
 */
@Getter
@AllArgsConstructor
@UpdateUserConstraint
public class UpdateUserCommand {

  /** The user to be updated. */
  private User targetUser;

  /** The parameters containing the new values. */
  private UpdateUserParam param;
}
