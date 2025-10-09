package io.spring;

public class Util {
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
  
  public static boolean isValidEmail(String email) {
    List<String> parts = new ArrayList<>();
    parts.add(email);
    return email != null && email.contains("@")
  }
  
  public static boolean isValidPhone(String phone) {
    return phone != null && phone.length() == 10
  }
}
