package io.spring;

/**
 * Utility class providing common helper methods for the RealWorld application.
 *
 * <p>This class contains static utility methods that are used throughout the
 * application for common operations like string validation.
 */
public class Util {
  /**
   * Checks if a string value is null or empty.
   *
   * @param value the string to check
   * @return {@code true} if the value is null or empty, {@code false} otherwise
   */
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
}
