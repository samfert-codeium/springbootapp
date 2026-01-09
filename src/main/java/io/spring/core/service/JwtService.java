package io.spring.core.service;

import io.spring.core.user.User;
import java.util.Optional;
import org.springframework.stereotype.Service;

/**
 * Service interface for JSON Web Token (JWT) operations.
 *
 * <p>This interface defines the contract for JWT-based authentication operations,
 * including token generation and validation. JWTs are used to authenticate users
 * in the RealWorld application without maintaining server-side session state.
 *
 * <p>Implementations of this interface handle the actual JWT encoding/decoding
 * using libraries like JJWT, while the domain layer remains agnostic of the
 * specific implementation details.
 *
 * @see User
 * @see io.spring.infrastructure.service.DefaultJwtService
 */
@Service
public interface JwtService {
  /**
   * Generates a JWT token for the specified user.
   *
   * <p>The generated token typically contains the user's ID as the subject claim
   * and has an expiration time configured in the application properties.
   *
   * @param user the user to generate a token for
   * @return a signed JWT token string
   */
  String toToken(User user);

  /**
   * Extracts the subject (user ID) from a JWT token.
   *
   * <p>This method validates the token signature and expiration before
   * extracting the subject claim.
   *
   * @param token the JWT token to parse
   * @return an Optional containing the user ID if the token is valid,
   *         or empty if the token is invalid or expired
   */
  Optional<String> getSubFromToken(String token);
}
