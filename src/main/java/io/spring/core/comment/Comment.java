package io.spring.core.comment;

import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Represents a comment on an article in the RealWorld application.
 *
 * <p>This is a core domain entity that encapsulates all the information about a comment,
 * including its content, the author, and the article it belongs to. Comments are
 * identified by a unique ID and are associated with both a user (author) and an article.
 *
 * <p>The class uses Lombok annotations for boilerplate code generation:
 * <ul>
 *   <li>{@code @Getter} - Generates getter methods for all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor for MyBatis</li>
 *   <li>{@code @EqualsAndHashCode} - Generates equals and hashCode based on the id field</li>
 * </ul>
 *
 * @see CommentRepository
 * @see io.spring.core.article.Article
 * @see io.spring.core.user.User
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = "id")
public class Comment {
  /** The unique identifier for this comment. */
  private String id;

  /** The content/body of the comment. */
  private String body;

  /** The unique identifier of the user who authored this comment. */
  private String userId;

  /** The unique identifier of the article this comment belongs to. */
  private String articleId;

  /** The timestamp when this comment was created. */
  private DateTime createdAt;

  /**
   * Creates a new comment with the specified content.
   *
   * <p>A unique identifier is automatically generated using UUID, and the
   * creation timestamp is set to the current time.
   *
   * @param body the content of the comment
   * @param userId the unique identifier of the comment author
   * @param articleId the unique identifier of the article being commented on
   */
  public Comment(String body, String userId, String articleId) {
    this.id = UUID.randomUUID().toString();
    this.body = body;
    this.userId = userId;
    this.articleId = articleId;
    this.createdAt = new DateTime();
  }
}
