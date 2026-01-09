package io.spring.api;

import io.spring.api.exception.ResourceNotFoundException;
import io.spring.application.ProfileQueryService;
import io.spring.application.data.ProfileData;
import io.spring.core.user.FollowRelation;
import io.spring.core.user.User;
import io.spring.core.user.UserRepository;
import java.util.HashMap;
import java.util.Optional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for user profile operations.
 *
 * <p>This controller handles viewing profiles and managing follow relationships.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>GET /profiles/{username} - Get a user's profile</li>
 *   <li>POST /profiles/{username}/follow - Follow a user</li>
 *   <li>DELETE /profiles/{username}/follow - Unfollow a user</li>
 * </ul>
 *
 * @see ProfileData
 * @see FollowRelation
 */
@RestController
@RequestMapping(path = "profiles/{username}")
@AllArgsConstructor
public class ProfileApi {
  /** Service for querying profile data. */
  private ProfileQueryService profileQueryService;

  /** Repository for user persistence and follow relationships. */
  private UserRepository userRepository;

  /**
   * Retrieves a user's profile by username.
   *
   * @param username the username of the profile to retrieve
   * @param user the authenticated user (optional)
   * @return the profile data
   * @throws ResourceNotFoundException if the user is not found
   */
  @GetMapping
  public ResponseEntity getProfile(
      @PathVariable("username") String username, @AuthenticationPrincipal User user) {
    return profileQueryService
        .findByUsername(username, user)
        .map(this::profileResponse)
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Follows a user.
   *
   * @param username the username of the user to follow
   * @param user the authenticated user
   * @return the profile data with updated following status
   * @throws ResourceNotFoundException if the target user is not found
   */
  @PostMapping(path = "follow")
  public ResponseEntity follow(
      @PathVariable("username") String username, @AuthenticationPrincipal User user) {
    return userRepository
        .findByUsername(username)
        .map(
            target -> {
              FollowRelation followRelation = new FollowRelation(user.getId(), target.getId());
              userRepository.saveRelation(followRelation);
              return profileResponse(profileQueryService.findByUsername(username, user).get());
            })
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Unfollows a user.
   *
   * @param username the username of the user to unfollow
   * @param user the authenticated user
   * @return the profile data with updated following status
   * @throws ResourceNotFoundException if the target user or follow relation is not found
   */
  @DeleteMapping(path = "follow")
  public ResponseEntity unfollow(
      @PathVariable("username") String username, @AuthenticationPrincipal User user) {
    Optional<User> userOptional = userRepository.findByUsername(username);
    if (userOptional.isPresent()) {
      User target = userOptional.get();
      return userRepository
          .findRelation(user.getId(), target.getId())
          .map(
              relation -> {
                userRepository.removeRelation(relation);
                return profileResponse(profileQueryService.findByUsername(username, user).get());
              })
          .orElseThrow(ResourceNotFoundException::new);
    } else {
      throw new ResourceNotFoundException();
    }
  }

  /**
   * Wraps profile data in a response entity.
   *
   * @param profile the profile data to wrap
   * @return a response entity containing the profile data
   */
  private ResponseEntity profileResponse(ProfileData profile) {
    return ResponseEntity.ok(
        new HashMap<String, Object>() {
          {
            put("profile", profile);
          }
        });
  }
}
