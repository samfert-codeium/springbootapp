package io.spring.api.security;

import io.spring.core.service.JwtService;
import io.spring.core.user.UserRepository;
import java.io.IOException;
import java.util.Collections;
import java.util.Optional;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.web.filter.OncePerRequestFilter;

/**
 * JWT authentication filter for processing Bearer tokens.
 *
 * <p>This filter intercepts incoming requests and extracts JWT tokens from the
 * Authorization header. If a valid token is found, it authenticates the user
 * and sets the security context.
 *
 * <p>Token format: "Token {jwt}" or "Bearer {jwt}"
 *
 * @see JwtService
 * @see WebSecurityConfig
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
public class JwtTokenFilter extends OncePerRequestFilter {
  /** Repository for looking up users by ID. */
  @Autowired private UserRepository userRepository;

  /** Service for JWT token validation and parsing. */
  @Autowired private JwtService jwtService;

  /** The HTTP header name containing the JWT token. */
  private final String header = "Authorization";

  /**
   * Processes the JWT token from the request and authenticates the user.
   *
   * <p>This method extracts the JWT token from the Authorization header,
   * validates it, and if valid, sets the authenticated user in the security context.
   *
   * @param request the HTTP request
   * @param response the HTTP response
   * @param filterChain the filter chain
   * @throws ServletException if a servlet error occurs
   * @throws IOException if an I/O error occurs
   */
  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {
    getTokenString(request.getHeader(header))
        .flatMap(token -> jwtService.getSubFromToken(token))
        .ifPresent(
            id -> {
              if (SecurityContextHolder.getContext().getAuthentication() == null) {
                userRepository
                    .findById(id)
                    .ifPresent(
                        user -> {
                          UsernamePasswordAuthenticationToken authenticationToken =
                              new UsernamePasswordAuthenticationToken(
                                  user, null, Collections.emptyList());
                          authenticationToken.setDetails(
                              new WebAuthenticationDetailsSource().buildDetails(request));
                          SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                        });
              }
            });

    filterChain.doFilter(request, response);
  }

  /**
   * Extracts the token string from the Authorization header.
   *
   * <p>The header is expected to be in the format "Token {jwt}" or "Bearer {jwt}".
   *
   * @param header the Authorization header value
   * @return an Optional containing the token string, or empty if not present
   */
  private Optional<String> getTokenString(String header) {
    if (header == null) {
      return Optional.empty();
    } else {
      String[] split = header.split(" ");
      if (split.length < 2) {
        return Optional.empty();
      } else {
        return Optional.ofNullable(split[1]);
      }
    }
  }
}
