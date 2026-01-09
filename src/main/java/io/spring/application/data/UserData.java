package io.spring.application.data;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user information.
 *
 * <p>This class represents user data as stored in the database and used
 * internally by the application. It contains all user profile information
 * except for sensitive data like passwords.
 *
 * @see io.spring.core.user.User
 * @see UserWithToken
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserData {
  /** The unique identifier of the user. */
  private String id;

  /** The user's email address. */
  private String email;

  /** The user's public username. */
  private String username;

  /** The user's biography or description. */
  private String bio;

  /** The URL to the user's profile image. */
  private String image;
}
