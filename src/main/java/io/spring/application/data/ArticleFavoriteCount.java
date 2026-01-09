package io.spring.application.data;

import lombok.Value;

/**
 * Data Transfer Object (DTO) for article favorite counts.
 *
 * <p>This immutable class represents the favorite count for a specific article,
 * used for batch retrieval of favorite counts in the query service.
 *
 * @see io.spring.application.ArticleQueryService
 */
@Value
public class ArticleFavoriteCount {
  /** The unique identifier of the article. */
  private String id;

  /** The number of users who have favorited this article. */
  private Integer count;
}
