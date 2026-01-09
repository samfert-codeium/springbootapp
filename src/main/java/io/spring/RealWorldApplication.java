package io.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Main entry point for the RealWorld Spring Boot application.
 *
 * <p>This is a demonstration application that implements the RealWorld API specification,
 * showcasing a fully-featured full-stack application built with Spring Boot and MyBatis.
 * The application includes CRUD operations, authentication, routing, pagination, and more.
 *
 * <p>The application follows Domain-Driven Design (DDD) principles and supports both
 * REST and GraphQL APIs for accessing the same domain logic.
 *
 * <p>Key features:
 * <ul>
 *   <li>User registration and authentication with JWT</li>
 *   <li>Article CRUD operations with tagging support</li>
 *   <li>Comment system for articles</li>
 *   <li>User following/followers functionality</li>
 *   <li>Article favoriting system</li>
 * </ul>
 *
 * @see <a href="https://github.com/gothinkster/realworld">RealWorld Specification</a>
 */
@SpringBootApplication
public class RealWorldApplication {

  /**
   * Application entry point.
   *
   * <p>Bootstraps the Spring Boot application by initializing the Spring context
   * and starting the embedded web server.
   *
   * @param args command-line arguments passed to the application
   */
  public static void main(String[] args) {
    SpringApplication.run(RealWorldApplication.class, args);
  }
}
