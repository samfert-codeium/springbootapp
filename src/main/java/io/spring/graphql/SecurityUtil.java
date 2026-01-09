package io.spring.graphql;

import io.spring.core.user.User;
import java.util.Optional;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

/**
 * Utility class for security-related operations in GraphQL resolvers.
 *
 * <p>Provides methods to access the currently authenticated user from
 * the Spring Security context.
 */
public class SecurityUtil {
  /**
   * Gets the currently authenticated user from the security context.
   *
   * @return an Optional containing the current user, or empty if not authenticated
   */
  public static Optional<User> getCurrentUser() {
    Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
    if (authentication instanceof AnonymousAuthenticationToken
        || authentication.getPrincipal() == null) {
      return Optional.empty();
    }
    io.spring.core.user.User currentUser = (io.spring.core.user.User) authentication.getPrincipal();
    return Optional.of(currentUser);
  }
}
