package io.spring.application.data;

import lombok.Getter;

/**
 * Data Transfer Object (DTO) for user information with authentication token.
 *
 * <p>This class combines user profile data with a JWT authentication token,
 * used in API responses after successful login or registration.
 *
 * @see UserData
 * @see io.spring.core.service.JwtService
 */
@Getter
public class UserWithToken {
  /** The user's email address. */
  private String email;

  /** The user's public username. */
  private String username;

  /** The user's biography or description. */
  private String bio;

  /** The URL to the user's profile image. */
  private String image;

  /** The JWT authentication token for the user. */
  private String token;

  /**
   * Creates a new UserWithToken from user data and a token.
   *
   * @param userData the user's profile data
   * @param token the JWT authentication token
   */
  public UserWithToken(UserData userData, String token) {
    this.email = userData.getEmail();
    this.username = userData.getUsername();
    this.bio = userData.getBio();
    this.image = userData.getImage();
    this.token = token;
  }
}
