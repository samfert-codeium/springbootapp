package io.spring.application.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Data Transfer Object (DTO) for user profile information.
 *
 * <p>This class represents a user's public profile as returned by the API,
 * including their username, bio, profile image, and whether the current user
 * is following them.
 *
 * @see io.spring.core.user.User
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfileData {
  /** The unique identifier of the user (not included in JSON). */
  @JsonIgnore private String id;

  /** The user's public username. */
  private String username;

  /** The user's biography or description. */
  private String bio;

  /** The URL to the user's profile image. */
  private String image;

  /** Whether the current user is following this profile. */
  private boolean following;
}
