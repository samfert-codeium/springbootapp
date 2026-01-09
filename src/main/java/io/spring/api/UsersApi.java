package io.spring.api;

import static org.springframework.web.bind.annotation.RequestMethod.POST;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.spring.api.exception.InvalidAuthenticationException;
import io.spring.application.UserQueryService;
import io.spring.application.data.UserData;
import io.spring.application.data.UserWithToken;
import io.spring.application.user.RegisterParam;
import io.spring.application.user.UserService;
import io.spring.core.service.JwtService;
import io.spring.core.user.User;
import io.spring.core.user.UserRepository;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import javax.validation.Valid;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for user registration and authentication.
 *
 * <p>This controller handles user registration and login operations.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>POST /users - Register a new user</li>
 *   <li>POST /users/login - Authenticate a user</li>
 * </ul>
 *
 * @see User
 * @see CurrentUserApi
 */
@RestController
@AllArgsConstructor
public class UsersApi {
  /** Repository for user persistence. */
  private UserRepository userRepository;

  /** Service for querying user data. */
  private UserQueryService userQueryService;

  /** Encoder for password verification. */
  private PasswordEncoder passwordEncoder;

  /** Service for JWT token generation. */
  private JwtService jwtService;

  /** Service for user write operations. */
  private UserService userService;

  /**
   * Registers a new user.
   *
   * @param registerParam the registration parameters
   * @return the created user data with JWT token (201 status)
   */
  @RequestMapping(path = "/users", method = POST)
  public ResponseEntity createUser(@Valid @RequestBody RegisterParam registerParam) {
    User user = userService.createUser(registerParam);
    UserData userData = userQueryService.findById(user.getId()).get();
    return ResponseEntity.status(201)
        .body(userResponse(new UserWithToken(userData, jwtService.toToken(user))));
  }

  /**
   * Authenticates a user and returns a JWT token.
   *
   * @param loginParam the login credentials
   * @return the user data with JWT token
   * @throws InvalidAuthenticationException if credentials are invalid
   */
  @RequestMapping(path = "/users/login", method = POST)
  public ResponseEntity userLogin(@Valid @RequestBody LoginParam loginParam) {
    Optional<User> optional = userRepository.findByEmail(loginParam.getEmail());
    if (optional.isPresent()
        && passwordEncoder.matches(loginParam.getPassword(), optional.get().getPassword())) {
      UserData userData = userQueryService.findById(optional.get().getId()).get();
      return ResponseEntity.ok(
          userResponse(new UserWithToken(userData, jwtService.toToken(optional.get()))));
    } else {
      throw new InvalidAuthenticationException();
    }
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

/**
 * Parameter object for user login.
 *
 * @see UsersApi#userLogin(LoginParam)
 */
@Getter
@JsonRootName("user")
@NoArgsConstructor
class LoginParam {
  /** The user's email address. Must be a valid email format. */
  @NotBlank(message = "can't be empty")
  @Email(message = "should be an email")
  private String email;

  /** The user's password. Must not be blank. */
  @NotBlank(message = "can't be empty")
  private String password;
}
