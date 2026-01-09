package io.spring.infrastructure.mybatis.readservice;

import io.spring.application.data.UserData;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis read service for user queries.
 *
 * <p>This service provides read-only queries for user data.
 *
 * @see UserData
 */
@Mapper
public interface UserReadService {

  /**
   * Finds a user by username.
   *
   * @param username the username
   * @return the user data, or null if not found
   */
  UserData findByUsername(@Param("username") String username);

  /**
   * Finds a user by ID.
   *
   * @param id the user ID
   * @return the user data, or null if not found
   */
  UserData findById(@Param("id") String id);
}
