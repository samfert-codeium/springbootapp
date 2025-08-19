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

@SuppressWarnings("SpringJavaAutowiringInspection")
public class JwtTokenFilter extends OncePerRequestFilter {
  @Autowired private UserRepository userRepository;
  @Autowired private JwtService jwtService;
  private final String header = "Authorization";

  @Override
  protected void doFilterInternal(
      HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
      throws ServletException, IOException {

    try {
      Optional<String> tokenOpt = getTokenString(request.getHeader(header));
      if (tokenOpt.isPresent()) {
        String token = tokenOpt.get();
        Optional<String> userIdOpt = jwtService.getSubFromToken(token);

        if (userIdOpt.isPresent()
            && SecurityContextHolder.getContext().getAuthentication() == null) {
          String userId = userIdOpt.get();
          userRepository
              .findById(userId)
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
      }
    } catch (Exception e) {
      response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
      response.getWriter().write("{\"error\":\"JWT token processing failed\"}");
      response.setContentType("application/json");
      return;
    }

    filterChain.doFilter(request, response);
  }

  private Optional<String> getTokenString(String header) {
    if (header == null || header.trim().isEmpty()) {
      return Optional.empty();
    }

    if (!header.startsWith("Token ")) {
      return Optional.empty();
    }

    String token = header.substring(6).trim();
    if (token.isEmpty()) {
      return Optional.empty();
    }

    return Optional.of(token);
  }
}
