package io.spring.application.article;

import com.fasterxml.jackson.annotation.JsonRootName;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Parameter object for updating an existing article.
 *
 * <p>This class represents the input data for updating an article.
 * All fields are optional - only non-empty values will be applied.
 *
 * @see ArticleCommandService#updateArticle(io.spring.core.article.Article, UpdateArticleParam)
 */
@Getter
@NoArgsConstructor
@AllArgsConstructor
@JsonRootName("article")
public class UpdateArticleParam {
  /** The new title for the article. Empty string means no change. */
  private String title = "";

  /** The new body content for the article. Empty string means no change. */
  private String body = "";

  /** The new description for the article. Empty string means no change. */
  private String description = "";
}
