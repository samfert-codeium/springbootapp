package io.spring.core.article;

import java.util.UUID;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;

/**
 * Represents a tag that can be associated with articles.
 *
 * <p>Tags are used to categorize and organize articles, making it easier for users
 * to discover content related to specific topics. Each tag has a unique identifier
 * and a name. Tags are considered equal if they have the same name, regardless of
 * their ID.
 *
 * <p>The class uses Lombok annotations for boilerplate code generation:
 * <ul>
 *   <li>{@code @Data} - Generates getters, setters, toString, equals, and hashCode</li>
 *   <li>{@code @NoArgsConstructor} - Generates a no-argument constructor for MyBatis</li>
 *   <li>{@code @EqualsAndHashCode(of = "name")} - Uses only the name field for equality</li>
 * </ul>
 *
 * @see Article
 */
@NoArgsConstructor
@Data
@EqualsAndHashCode(of = "name")
public class Tag {
  /** The unique identifier for this tag. */
  private String id;

  /** The display name of the tag. */
  private String name;

  /**
   * Creates a new tag with the specified name.
   *
   * <p>A unique identifier is automatically generated using UUID.
   *
   * @param name the display name for the tag
   */
  public Tag(String name) {
    this.id = UUID.randomUUID().toString();
    this.name = name;
  }
}
