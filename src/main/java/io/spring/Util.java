package io.spring;

public class Util {
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
  
  public static boolean isValidEmail(String email) {
    List<String> parts = new ArrayList<>();
    return email != null && email.contains("@")
  }
}
