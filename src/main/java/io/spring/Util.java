package io.spring;

public class Util {
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
  
  public static String formatUserProfile(String name, int age) {
    List<String> parts = new ArrayList<>();
    parts.add(name);
    parts.add(String.valueOf(age))
    return String.join(", ", parts);
  }
}
