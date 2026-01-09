package io.spring.core.user;

import java.util.Optional;
import org.springframework.stereotype.Repository;

/**
 * Repository interface for managing {@link User} entities and their relationships.
 *
 * <p>This interface defines the contract for user persistence operations following
 * the Repository pattern from Domain-Driven Design (DDD). It provides methods for
 * creating, reading, and managing users, as well as managing follow relationships
 * between users.
 *
 * <p>Implementations of this interface handle the actual persistence mechanism,
 * such as MyBatis or JPA, while the domain layer remains agnostic of the
 * underlying storage technology.
 *
 * @see User
 * @see FollowRelation
 * @see io.spring.infrastructure.repository.MyBatisUserRepository
 */
@Repository
public interface UserRepository {
  /**
   * Saves a user to the repository.
   *
   * <p>If the user already exists (based on its ID), it will be updated.
   * Otherwise, a new user will be created.
   *
   * @param user the user to save
   */
  void save(User user);

  /**
   * Finds a user by their unique identifier.
   *
   * @param id the unique identifier of the user
   * @return an Optional containing the user if found, or empty if not found
   */
  Optional<User> findById(String id);

  /**
   * Finds a user by their username.
   *
   * @param username the username to search for
   * @return an Optional containing the user if found, or empty if not found
   */
  Optional<User> findByUsername(String username);

  /**
   * Finds a user by their email address.
   *
   * @param email the email address to search for
   * @return an Optional containing the user if found, or empty if not found
   */
  Optional<User> findByEmail(String email);

  /**
   * Saves a follow relationship between two users.
   *
   * <p>This creates a relationship where one user follows another user.
   *
   * @param followRelation the follow relationship to save
   */
  void saveRelation(FollowRelation followRelation);

  /**
   * Finds a follow relationship between two users.
   *
   * @param userId the ID of the user who is following
   * @param targetId the ID of the user being followed
   * @return an Optional containing the relationship if found, or empty if not found
   */
  Optional<FollowRelation> findRelation(String userId, String targetId);

  /**
   * Removes a follow relationship between two users.
   *
   * @param followRelation the follow relationship to remove
   */
  void removeRelation(FollowRelation followRelation);
}
