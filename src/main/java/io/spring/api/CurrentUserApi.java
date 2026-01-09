package io.spring.api;

import io.spring.application.UserQueryService;
import io.spring.application.data.UserData;
import io.spring.application.data.UserWithToken;
import io.spring.application.user.UpdateUserCommand;
import io.spring.application.user.UpdateUserParam;
import io.spring.application.user.UserService;
import io.spring.core.user.User;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for current user operations.
 *
 * <p>This controller handles operations for the currently authenticated user,
 * including retrieving and updating their profile.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>GET /user - Get current user's profile</li>
 *   <li>PUT /user - Update current user's profile</li>
 * </ul>
 *
 * @see User
 * @see UsersApi
 */
@RestController
@RequestMapping(path = "/user")
@AllArgsConstructor
public class CurrentUserApi {

  /** Service for querying user data. */
  private UserQueryService userQueryService;

  /** Service for user write operations. */
  private UserService userService;

  /**
   * Retrieves the current user's profile.
   *
   * @param currentUser the authenticated user
   * @param authorization the Authorization header containing the JWT token
   * @return the user data with the current token
   */
  @GetMapping
  public ResponseEntity currentUser(
      @AuthenticationPrincipal User currentUser,
      @RequestHeader(value = "Authorization") String authorization) {
    UserData userData = userQueryService.findById(currentUser.getId()).get();
    return ResponseEntity.ok(
        userResponse(new UserWithToken(userData, authorization.split(" ")[1])));
  }

  /**
   * Updates the current user's profile.
   *
   * @param currentUser the authenticated user
   * @param token the Authorization header containing the JWT token
   * @param updateUserParam the update parameters
   * @return the updated user data with the current token
   */
  @PutMapping
  public ResponseEntity updateProfile(
      @AuthenticationPrincipal User currentUser,
      @RequestHeader("Authorization") String token,
      @Valid @RequestBody UpdateUserParam updateUserParam) {

    userService.updateUser(new UpdateUserCommand(currentUser, updateUserParam));
    UserData userData = userQueryService.findById(currentUser.getId()).get();
    return ResponseEntity.ok(userResponse(new UserWithToken(userData, token.split(" ")[1])));
  }

  /**
   * Wraps user data with token in a response map.
   *
   * @param userWithToken the user data with token
   * @return a map containing the user data
   */
  private Map<String, Object> userResponse(UserWithToken userWithToken) {
    return new HashMap<String, Object>() {
      {
        put("user", userWithToken);
      }
    };
  }
}
