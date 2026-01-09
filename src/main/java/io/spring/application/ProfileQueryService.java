package io.spring.application;

import io.spring.application.data.ProfileData;
import io.spring.application.data.UserData;
import io.spring.core.user.User;
import io.spring.infrastructure.mybatis.readservice.UserReadService;
import io.spring.infrastructure.mybatis.readservice.UserRelationshipQueryService;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

/**
 * Service class for querying user profile data following the CQRS pattern.
 *
 * <p>This service is responsible for reading user profile data and enriching it
 * with information about whether the current user is following the profile owner.
 *
 * @see ProfileData
 * @see UserData
 */
@Component
@AllArgsConstructor
public class ProfileQueryService {
  /** Service for reading user data from the database. */
  private UserReadService userReadService;

  /** Service for querying user relationship data. */
  private UserRelationshipQueryService userRelationshipQueryService;

  /**
   * Finds a user profile by username.
   *
   * <p>If a current user is provided, the profile data is enriched with information
   * about whether the current user is following the profile owner.
   *
   * @param username the username to search for
   * @param currentUser the current user, or null if not authenticated
   * @return an Optional containing the profile data if found, or empty if not found
   */
  public Optional<ProfileData> findByUsername(String username, User currentUser) {
    UserData userData = userReadService.findByUsername(username);
    if (userData == null) {
      return Optional.empty();
    } else {
      ProfileData profileData =
          new ProfileData(
              userData.getId(),
              userData.getUsername(),
              userData.getBio(),
              userData.getImage(),
              currentUser != null
                  && userRelationshipQueryService.isUserFollowing(
                      currentUser.getId(), userData.getId()));
      return Optional.of(profileData);
    }
  }
}
