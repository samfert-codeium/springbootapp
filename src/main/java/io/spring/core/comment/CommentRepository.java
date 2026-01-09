package io.spring.core.comment;

import java.util.Optional;

/**
 * Repository interface for managing {@link Comment} entities.
 *
 * <p>This interface defines the contract for comment persistence operations following
 * the Repository pattern from Domain-Driven Design (DDD). It provides methods for
 * creating, reading, and deleting comments.
 *
 * <p>Implementations of this interface handle the actual persistence mechanism,
 * such as MyBatis or JPA, while the domain layer remains agnostic of the
 * underlying storage technology.
 *
 * @see Comment
 * @see io.spring.infrastructure.repository.MyBatisCommentRepository
 */
public interface CommentRepository {
  /**
   * Saves a comment to the repository.
   *
   * @param comment the comment to save
   */
  void save(Comment comment);

  /**
   * Finds a comment by its ID within a specific article.
   *
   * @param articleId the unique identifier of the article
   * @param id the unique identifier of the comment
   * @return an Optional containing the comment if found, or empty if not found
   */
  Optional<Comment> findById(String articleId, String id);

  /**
   * Removes a comment from the repository.
   *
   * @param comment the comment to remove
   */
  void remove(Comment comment);
}
