package io.spring.infrastructure.mybatis.readservice;

import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis read service for user relationship queries.
 *
 * <p>This service provides read-only queries for follow relationships.
 */
@Mapper
public interface UserRelationshipQueryService {
  /**
   * Checks if a user is following another user.
   *
   * @param userId the follower's user ID
   * @param anotherUserId the followed user's ID
   * @return true if the user is following the other user
   */
  boolean isUserFollowing(
      @Param("userId") String userId, @Param("anotherUserId") String anotherUserId);

  /**
   * Gets the set of authors that a user is following from a given list.
   *
   * @param userId the user ID
   * @param ids the list of author IDs to check
   * @return a set of author IDs that the user is following
   */
  Set<String> followingAuthors(@Param("userId") String userId, @Param("ids") List<String> ids);

  /**
   * Gets all users that a user is following.
   *
   * @param userId the user ID
   * @return a list of user IDs that the user is following
   */
  List<String> followedUsers(@Param("userId") String userId);
}
