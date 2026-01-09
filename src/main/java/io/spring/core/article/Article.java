package io.spring.core.article;

import static java.util.stream.Collectors.toList;

import io.spring.Util;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.joda.time.DateTime;

/**
 * Represents an article in the RealWorld application.
 *
 * <p>This is a core domain entity that encapsulates all the information about an article,
 * including its content, metadata, and associated tags. Articles are identified by a unique ID
 * and can be accessed via a URL-friendly slug derived from the title.
 *
 * <p>The class uses Lombok annotations for boilerplate code generation:
 * <ul>
 *   <li>{@code @Getter} - Generates getter methods for all fields</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor for MyBatis</li>
 *   <li>{@code @EqualsAndHashCode} - Generates equals and hashCode based on the id field</li>
 * </ul>
 *
 * @see Tag
 * @see ArticleRepository
 */
@Getter
@NoArgsConstructor
@EqualsAndHashCode(of = {"id"})
public class Article {
  /** The unique identifier of the user who authored this article. */
  private String userId;

  /** The unique identifier for this article. */
  private String id;

  /** The URL-friendly slug derived from the article title. */
  private String slug;

  /** The title of the article. */
  private String title;

  /** A brief description or summary of the article. */
  private String description;

  /** The main content/body of the article in markdown format. */
  private String body;

  /** The list of tags associated with this article. */
  private List<Tag> tags;

  /** The timestamp when this article was created. */
  private DateTime createdAt;

  /** The timestamp when this article was last updated. */
  private DateTime updatedAt;

  /**
   * Creates a new article with the current timestamp.
   *
   * @param title the title of the article
   * @param description a brief description of the article
   * @param body the main content of the article
   * @param tagList a list of tag names to associate with the article
   * @param userId the unique identifier of the author
   */
  public Article(
      String title, String description, String body, List<String> tagList, String userId) {
    this(title, description, body, tagList, userId, new DateTime());
  }

  /**
   * Creates a new article with a specified creation timestamp.
   *
   * <p>This constructor generates a unique ID using UUID, creates a URL-friendly slug
   * from the title, and converts the tag name list into Tag objects. Duplicate tags
   * are automatically removed using a HashSet.
   *
   * @param title the title of the article
   * @param description a brief description of the article
   * @param body the main content of the article
   * @param tagList a list of tag names to associate with the article
   * @param userId the unique identifier of the author
   * @param createdAt the timestamp for when the article was created
   */
  public Article(
      String title,
      String description,
      String body,
      List<String> tagList,
      String userId,
      DateTime createdAt) {
    this.id = UUID.randomUUID().toString();
    this.slug = toSlug(title);
    this.title = title;
    this.description = description;
    this.body = body;
    this.tags = new HashSet<>(tagList).stream().map(Tag::new).collect(toList());
    this.userId = userId;
    this.createdAt = createdAt;
    this.updatedAt = createdAt;
  }

  /**
   * Updates the article with new values.
   *
   * <p>Only non-empty values will be applied. If a field is updated, the {@code updatedAt}
   * timestamp is automatically set to the current time. When the title is updated,
   * the slug is also regenerated.
   *
   * @param title the new title, or null/empty to keep the current title
   * @param description the new description, or null/empty to keep the current description
   * @param body the new body content, or null/empty to keep the current body
   */
  public void update(String title, String description, String body) {
    if (!Util.isEmpty(title)) {
      this.title = title;
      this.slug = toSlug(title);
      this.updatedAt = new DateTime();
    }
    if (!Util.isEmpty(description)) {
      this.description = description;
      this.updatedAt = new DateTime();
    }
    if (!Util.isEmpty(body)) {
      this.body = body;
      this.updatedAt = new DateTime();
    }
  }

  /**
   * Converts a title string into a URL-friendly slug.
   *
   * <p>The conversion process:
   * <ol>
   *   <li>Converts the title to lowercase</li>
   *   <li>Replaces special characters, CJK punctuation, quotes, whitespace,
   *       question marks, commas, and periods with hyphens</li>
   * </ol>
   *
   * @param title the title to convert
   * @return a URL-friendly slug representation of the title
   */
  public static String toSlug(String title) {
    return title.toLowerCase().replaceAll("[\\&|[\\uFE30-\\uFFA0]|\\’|\\”|\\s\\?\\,\\.]+", "-");
  }
}
