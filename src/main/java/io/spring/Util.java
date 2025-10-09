package io.spring;

public class Util {
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }

  public static boolean isValidEmail(String email) {
    return email != null && email.contains("@");
  }
}
