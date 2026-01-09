package io.spring.infrastructure.mybatis.mapper;

import io.spring.core.user.FollowRelation;
import io.spring.core.user.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis mapper interface for user operations.
 *
 * <p>This mapper provides database operations for users and follow relationships,
 * including CRUD operations and relationship management.
 *
 * @see User
 * @see FollowRelation
 */
@Mapper
public interface UserMapper {
  /**
   * Inserts a new user into the database.
   *
   * @param user the user to insert
   */
  void insert(@Param("user") User user);

  /**
   * Finds a user by username.
   *
   * @param username the username
   * @return the user, or null if not found
   */
  User findByUsername(@Param("username") String username);

  /**
   * Finds a user by email address.
   *
   * @param email the email address
   * @return the user, or null if not found
   */
  User findByEmail(@Param("email") String email);

  /**
   * Finds a user by ID.
   *
   * @param id the user ID
   * @return the user, or null if not found
   */
  User findById(@Param("id") String id);

  /**
   * Updates an existing user.
   *
   * @param user the user with updated values
   */
  void update(@Param("user") User user);

  /**
   * Finds a follow relationship between two users.
   *
   * @param userId the follower's user ID
   * @param targetId the followed user's ID
   * @return the follow relationship, or null if not found
   */
  FollowRelation findRelation(@Param("userId") String userId, @Param("targetId") String targetId);

  /**
   * Saves a new follow relationship.
   *
   * @param followRelation the follow relationship to save
   */
  void saveRelation(@Param("followRelation") FollowRelation followRelation);

  /**
   * Deletes a follow relationship.
   *
   * @param followRelation the follow relationship to delete
   */
  void deleteRelation(@Param("followRelation") FollowRelation followRelation);
}
