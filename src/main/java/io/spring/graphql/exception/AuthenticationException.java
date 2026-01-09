package io.spring.graphql.exception;

/**
 * Exception thrown when a GraphQL operation requires authentication.
 *
 * <p>This exception is thrown when a user attempts to perform a GraphQL
 * mutation or query that requires authentication but is not logged in.
 */
public class AuthenticationException extends RuntimeException {}
