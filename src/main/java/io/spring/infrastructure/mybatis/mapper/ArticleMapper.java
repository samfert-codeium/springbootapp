package io.spring.infrastructure.mybatis.mapper;

import io.spring.core.article.Article;
import io.spring.core.article.Tag;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis mapper interface for article operations.
 *
 * <p>This mapper provides database operations for articles and tags,
 * including CRUD operations and tag relationship management.
 *
 * @see Article
 * @see Tag
 */
@Mapper
public interface ArticleMapper {
  /**
   * Inserts a new article into the database.
   *
   * @param article the article to insert
   */
  void insert(@Param("article") Article article);

  /**
   * Finds an article by its ID.
   *
   * @param id the article ID
   * @return the article, or null if not found
   */
  Article findById(@Param("id") String id);

  /**
   * Finds a tag by its name.
   *
   * @param tagName the tag name
   * @return the tag, or null if not found
   */
  Tag findTag(@Param("tagName") String tagName);

  /**
   * Inserts a new tag into the database.
   *
   * @param tag the tag to insert
   */
  void insertTag(@Param("tag") Tag tag);

  /**
   * Creates a relationship between an article and a tag.
   *
   * @param articleId the article ID
   * @param tagId the tag ID
   */
  void insertArticleTagRelation(@Param("articleId") String articleId, @Param("tagId") String tagId);

  /**
   * Finds an article by its slug.
   *
   * @param slug the article slug
   * @return the article, or null if not found
   */
  Article findBySlug(@Param("slug") String slug);

  /**
   * Updates an existing article.
   *
   * @param article the article with updated values
   */
  void update(@Param("article") Article article);

  /**
   * Deletes an article by its ID.
   *
   * @param id the article ID
   */
  void delete(@Param("id") String id);
}
