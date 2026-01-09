package io.spring.application;

import io.spring.application.data.UserData;
import io.spring.infrastructure.mybatis.readservice.UserReadService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for querying user data following the CQRS pattern.
 *
 * <p>This service is responsible for reading user data from the database.
 * It provides a simple interface for retrieving user information by ID.
 *
 * @see UserData
 */
@Service
@AllArgsConstructor
public class UserQueryService {
  /** Service for reading user data from the database. */
  private UserReadService userReadService;

  /**
   * Finds a user by their unique identifier.
   *
   * @param id the unique identifier of the user
   * @return an Optional containing the user data if found, or empty if not found
   */
  public Optional<UserData> findById(String id) {
    return Optional.ofNullable(userReadService.findById(id));
  }
}
