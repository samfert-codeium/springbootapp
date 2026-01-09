package io.spring.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import io.spring.application.DateTimeCursor;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Data Transfer Object (DTO) for article information.
 *
 * <p>This class represents article data as returned by the API, including
 * the article content, metadata, author profile, and user-specific information
 * such as whether the current user has favorited the article.
 *
 * <p>Implements {@link io.spring.application.Node} to support cursor-based
 * pagination using the article's update timestamp.
 *
 * @see ProfileData
 * @see io.spring.core.article.Article
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ArticleData implements io.spring.application.Node {
  /** The unique identifier of the article. */
  private String id;

  /** The URL-friendly slug of the article. */
  private String slug;

  /** The title of the article. */
  private String title;

  /** A brief description of the article. */
  private String description;

  /** The main content of the article. */
  private String body;

  /** Whether the current user has favorited this article. */
  private boolean favorited;

  /** The total number of users who have favorited this article. */
  private int favoritesCount;

  /** The timestamp when the article was created. */
  private DateTime createdAt;

  /** The timestamp when the article was last updated. */
  private DateTime updatedAt;

  /** The list of tags associated with this article. */
  private List<String> tagList;

  /** The profile data of the article's author. */
  @JsonProperty("author")
  private ProfileData profileData;

  /**
   * Gets the cursor for this article for pagination purposes.
   *
   * <p>Uses the article's update timestamp as the cursor value.
   *
   * @return a DateTimeCursor based on the article's update timestamp
   */
  @Override
  public DateTimeCursor getCursor() {
    return new DateTimeCursor(updatedAt);
  }
}
