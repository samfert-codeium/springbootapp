package io.spring.application.data;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import io.spring.application.DateTimeCursor;
import io.spring.application.Node;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Data Transfer Object (DTO) for comment information.
 *
 * <p>This class represents comment data as returned by the API, including
 * the comment content, timestamps, and author profile information.
 *
 * <p>Implements {@link Node} to support cursor-based pagination using
 * the comment's creation timestamp.
 *
 * @see ProfileData
 * @see io.spring.core.comment.Comment
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentData implements Node {
  /** The unique identifier of the comment. */
  private String id;

  /** The content of the comment. */
  private String body;

  /** The ID of the article this comment belongs to (not included in JSON). */
  @JsonIgnore private String articleId;

  /** The timestamp when the comment was created. */
  private DateTime createdAt;

  /** The timestamp when the comment was last updated. */
  private DateTime updatedAt;

  /** The profile data of the comment's author. */
  @JsonProperty("author")
  private ProfileData profileData;

  /**
   * Gets the cursor for this comment for pagination purposes.
   *
   * <p>Uses the comment's creation timestamp as the cursor value.
   *
   * @return a DateTimeCursor based on the comment's creation timestamp
   */
  @Override
  public DateTimeCursor getCursor() {
    return new DateTimeCursor(createdAt);
  }
}
