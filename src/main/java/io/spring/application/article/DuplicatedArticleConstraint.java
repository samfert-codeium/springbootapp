package io.spring.application.article;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import javax.validation.Constraint;
import javax.validation.Payload;

/**
 * Validation constraint annotation for checking duplicate article titles.
 *
 * <p>This constraint ensures that an article title doesn't result in a slug
 * that already exists in the database. It's used during article creation
 * to prevent duplicate articles.
 *
 * @see DuplicatedArticleValidator
 */
@Documented
@Constraint(validatedBy = DuplicatedArticleValidator.class)
@Target({ElementType.METHOD, ElementType.FIELD, ElementType.PARAMETER, ElementType.TYPE_USE})
@Retention(RetentionPolicy.RUNTIME)
public @interface DuplicatedArticleConstraint {
  /**
   * The error message to display when validation fails.
   *
   * @return the error message
   */
  String message() default "article name exists";

  /**
   * Groups for constraint categorization.
   *
   * @return the validation groups
   */
  Class<?>[] groups() default {};

  /**
   * Payload for extensibility purposes.
   *
   * @return the payload classes
   */
  Class<? extends Payload>[] payload() default {};
}
