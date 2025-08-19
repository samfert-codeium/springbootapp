package io.spring.application.user;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

@Constraint(validatedBy = PasswordComplexityValidator.class)
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
public @interface PasswordComplexityConstraint {
  String message() default
      "Password must be at least 8 characters long and contain at least one uppercase letter, one lowercase letter, one digit, and one special character";

  Class<?>[] groups() default {};

  Class<? extends Payload>[] payload() default {};
}
