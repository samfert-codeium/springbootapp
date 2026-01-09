package io.spring.core.favorite;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;

/**
 * Represents a favorite relationship between a user and an article.
 *
 * <p>This is a domain entity that models the action of a user favoriting (liking)
 * an article. The relationship is bidirectional: it links a user to an article
 * they have favorited.
 *
 * <p>The class uses Lombok annotations for boilerplate code generation:
 * <ul>
 *   <li>{@code @Getter} - Generates getter methods for all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor for MyBatis</li>
 *   <li>{@code @EqualsAndHashCode} - Generates equals and hashCode based on all fields</li>
 * </ul>
 *
 * @see ArticleFavoriteRepository
 * @see io.spring.core.article.Article
 * @see io.spring.core.user.User
 */
@NoArgsConstructor
@Getter
@EqualsAndHashCode
public class ArticleFavorite {
  /** The unique identifier of the favorited article. */
  private String articleId;

  /** The unique identifier of the user who favorited the article. */
  private String userId;

  /**
   * Creates a new article favorite relationship.
   *
   * @param articleId the unique identifier of the article being favorited
   * @param userId the unique identifier of the user favoriting the article
   */
  public ArticleFavorite(String articleId, String userId) {
    this.articleId = articleId;
    this.userId = userId;
  }
}
