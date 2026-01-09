package io.spring.core.article;

import java.util.Optional;

/**
 * Repository interface for managing {@link Article} entities.
 *
 * <p>This interface defines the contract for article persistence operations following
 * the Repository pattern from Domain-Driven Design (DDD). It provides methods for
 * creating, reading, updating, and deleting articles.
 *
 * <p>Implementations of this interface handle the actual persistence mechanism,
 * such as MyBatis or JPA, while the domain layer remains agnostic of the
 * underlying storage technology.
 *
 * @see Article
 * @see io.spring.infrastructure.repository.MyBatisArticleRepository
 */
public interface ArticleRepository {

  /**
   * Saves an article to the repository.
   *
   * <p>If the article already exists (based on its ID), it will be updated.
   * Otherwise, a new article will be created.
   *
   * @param article the article to save
   */
  void save(Article article);

  /**
   * Finds an article by its unique identifier.
   *
   * @param id the unique identifier of the article
   * @return an Optional containing the article if found, or empty if not found
   */
  Optional<Article> findById(String id);

  /**
   * Finds an article by its URL-friendly slug.
   *
   * @param slug the slug of the article
   * @return an Optional containing the article if found, or empty if not found
   */
  Optional<Article> findBySlug(String slug);

  /**
   * Removes an article from the repository.
   *
   * @param article the article to remove
   */
  void remove(Article article);
}
