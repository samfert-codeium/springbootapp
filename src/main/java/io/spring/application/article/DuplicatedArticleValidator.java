package io.spring.application.article;

import io.spring.application.ArticleQueryService;
import io.spring.core.article.Article;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Validator implementation for the {@link DuplicatedArticleConstraint}.
 *
 * <p>This validator checks if an article title would result in a slug that
 * already exists in the database. It converts the title to a slug and queries
 * the database to check for duplicates.
 *
 * @see DuplicatedArticleConstraint
 */
class DuplicatedArticleValidator
    implements ConstraintValidator<DuplicatedArticleConstraint, String> {

  /** Service for querying article data. */
  @Autowired private ArticleQueryService articleQueryService;

  /**
   * Validates that the article title doesn't result in a duplicate slug.
   *
   * @param value the article title to validate
   * @param context the constraint validator context
   * @return {@code true} if the slug doesn't exist, {@code false} otherwise
   */
  @Override
  public boolean isValid(String value, ConstraintValidatorContext context) {
    return !articleQueryService.findBySlug(Article.toSlug(value), null).isPresent();
  }
}
