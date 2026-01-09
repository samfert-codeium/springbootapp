package io.spring.core.user;

import io.spring.Util;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a user in the RealWorld application.
 *
 * <p>This is a core domain entity that encapsulates all the information about a user,
 * including authentication credentials and profile information. Users can create articles,
 * post comments, follow other users, and favorite articles.
 *
 * <p>The class uses Lombok annotations for boilerplate code generation:
 * <ul>
 *   <li>{@code @Getter} - Generates getter methods for all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor for MyBatis</li>
 *   <li>{@code @EqualsAndHashCode} - Generates equals and hashCode based on the id field</li>
 * </ul>
 *
 * @see UserRepository
 * @see FollowRelation
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class User {
  /** The unique identifier for this user. */
  private String id;

  /** The user's email address, used for authentication. */
  private String email;

  /** The user's unique username, displayed publicly. */
  private String username;

  /** The user's hashed password for authentication. */
  private String password;

  /** The user's biography or description. */
  private String bio;

  /** The URL to the user's profile image. */
  private String image;

  /**
   * Creates a new user with the specified profile information.
   *
   * <p>A unique identifier is automatically generated using UUID.
   *
   * @param email the user's email address
   * @param username the user's unique username
   * @param password the user's hashed password
   * @param bio the user's biography (can be null)
   * @param image the URL to the user's profile image (can be null)
   */
  public User(String email, String username, String password, String bio, String image) {
    this.id = UUID.randomUUID().toString();
    this.email = email;
    this.username = username;
    this.password = password;
    this.bio = bio;
    this.image = image;
  }

  /**
   * Updates the user's profile with new values.
   *
   * <p>Only non-empty values will be applied. Fields with null or empty values
   * will retain their current values.
   *
   * @param email the new email address, or null/empty to keep the current email
   * @param username the new username, or null/empty to keep the current username
   * @param password the new hashed password, or null/empty to keep the current password
   * @param bio the new biography, or null/empty to keep the current bio
   * @param image the new profile image URL, or null/empty to keep the current image
   */
  public void update(String email, String username, String password, String bio, String image) {
    if (!Util.isEmpty(email)) {
      this.email = email;
    }

    if (!Util.isEmpty(username)) {
      this.username = username;
    }

    if (!Util.isEmpty(password)) {
      this.password = password;
    }

    if (!Util.isEmpty(bio)) {
      this.bio = bio;
    }

    if (!Util.isEmpty(image)) {
      this.image = image;
    }
  }
}
