package io.spring.api.security;

import io.github.bucket4j.Bandwidth;
import io.github.bucket4j.Bucket;
import io.github.bucket4j.Refill;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.Duration;
import java.util.concurrent.ConcurrentHashMap;
import org.springframework.http.HttpStatus;
import org.springframework.web.filter.OncePerRequestFilter;

public class RateLimitingFilter extends OncePerRequestFilter {
  
  private final ConcurrentHashMap<String, Bucket> buckets = new ConcurrentHashMap<>();
  
  private Bucket createNewBucket() {
    Bandwidth limit = Bandwidth.classic(5, Refill.intervally(5, Duration.ofMinutes(1)));
    return Bucket.builder()
        .addLimit(limit)
        .build();
  }
  
  private String getClientKey(HttpServletRequest request) {
    String xForwardedFor = request.getHeader("X-Forwarded-For");
    if (xForwardedFor != null && !xForwardedFor.isEmpty()) {
      return xForwardedFor.split(",")[0].trim();
    }
    return request.getRemoteAddr();
  }
  
  @Override
  protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, 
                                  FilterChain filterChain) throws ServletException, IOException {
    
    String requestURI = request.getRequestURI();
    String method = request.getMethod();
    
    if (("/users".equals(requestURI) && "POST".equals(method)) || 
        ("/users/login".equals(requestURI) && "POST".equals(method))) {
      
      String clientKey = getClientKey(request);
      Bucket bucket = buckets.computeIfAbsent(clientKey, k -> createNewBucket());
      
      if (!bucket.tryConsume(1)) {
        response.setStatus(HttpStatus.TOO_MANY_REQUESTS.value());
        response.getWriter().write("{\"error\":\"Too many requests. Please try again later.\"}");
        response.setContentType("application/json");
        return;
      }
    }
    
    filterChain.doFilter(request, response);
  }
}
