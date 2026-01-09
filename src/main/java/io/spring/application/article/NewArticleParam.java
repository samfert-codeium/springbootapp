package io.spring.application.article;

import com.fasterxml.jackson.annotation.JsonRootName;
import java.util.List;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Parameter object for creating a new article.
 *
 * <p>This class represents the input data required to create a new article,
 * including validation constraints to ensure data integrity.
 *
 * @see ArticleCommandService#createArticle(NewArticleParam, io.spring.core.user.User)
 */
@Getter
@JsonRootName("article")
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class NewArticleParam {
  /**
   * The title of the article.
   * Must not be blank and must not duplicate an existing article's slug.
   */
  @NotBlank(message = "can't be empty")
  @DuplicatedArticleConstraint
  private String title;

  /** A brief description of the article. Must not be blank. */
  @NotBlank(message = "can't be empty")
  private String description;

  /** The main content of the article. Must not be blank. */
  @NotBlank(message = "can't be empty")
  private String body;

  /** Optional list of tags to associate with the article. */
  private List<String> tagList;
}
