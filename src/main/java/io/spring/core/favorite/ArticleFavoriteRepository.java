package io.spring.core.favorite;

import java.util.Optional;

/**
 * Repository interface for managing {@link ArticleFavorite} entities.
 *
 * <p>This interface defines the contract for article favorite persistence operations
 * following the Repository pattern from Domain-Driven Design (DDD). It provides
 * methods for creating, reading, and deleting favorite relationships between
 * users and articles.
 *
 * <p>Implementations of this interface handle the actual persistence mechanism,
 * such as MyBatis or JPA, while the domain layer remains agnostic of the
 * underlying storage technology.
 *
 * @see ArticleFavorite
 * @see io.spring.infrastructure.repository.MyBatisArticleFavoriteRepository
 */
public interface ArticleFavoriteRepository {
  /**
   * Saves an article favorite relationship to the repository.
   *
   * @param articleFavorite the favorite relationship to save
   */
  void save(ArticleFavorite articleFavorite);

  /**
   * Finds a favorite relationship between a user and an article.
   *
   * @param articleId the unique identifier of the article
   * @param userId the unique identifier of the user
   * @return an Optional containing the favorite if found, or empty if not found
   */
  Optional<ArticleFavorite> find(String articleId, String userId);

  /**
   * Removes an article favorite relationship from the repository.
   *
   * @param favorite the favorite relationship to remove
   */
  void remove(ArticleFavorite favorite);
}
