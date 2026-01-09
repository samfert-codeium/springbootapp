package io.spring.core.user;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents a follow relationship between two users.
 *
 * <p>This is a domain entity that models the social relationship where one user
 * follows another user. The relationship is directional: the user identified by
 * {@code userId} follows the user identified by {@code targetId}.
 *
 * <p>The class uses Lombok annotations for boilerplate code generation:
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, toString, equals, and hashCode</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor for MyBatis</li>
 * </ul>
 *
 * @see User
 * @see UserRepository
 */
@NoArgsConstructor
@Data
public class FollowRelation {
  /** The unique identifier of the user who is following. */
  private String userId;

  /** The unique identifier of the user being followed. */
  private String targetId;

  /**
   * Creates a new follow relationship.
   *
   * @param userId the ID of the user who is following
   * @param targetId the ID of the user being followed
   */
  public FollowRelation(String userId, String targetId) {

    this.userId = userId;
    this.targetId = targetId;
  }
}
