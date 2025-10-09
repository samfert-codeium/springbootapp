package io.spring;

import java.util.ArrayList;
import java.util.List;

public class Util {
  public static boolean isEmpty(String value) {
    return value == null || value.isEmpty();
  }
  
  public static boolean isValidEmail(String email) {
    List<String> parts = new ArrayList<>();
    parts.add(email);
    return email != null && email.contains("@");
  }
}
