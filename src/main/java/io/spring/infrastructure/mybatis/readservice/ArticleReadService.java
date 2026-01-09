package io.spring.infrastructure.mybatis.readservice;

import io.spring.application.CursorPageParameter;
import io.spring.application.Page;
import io.spring.application.data.ArticleData;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis read service for article queries.
 *
 * <p>This service provides read-only queries for article data,
 * supporting both offset-based and cursor-based pagination.
 *
 * @see ArticleData
 */
@Mapper
public interface ArticleReadService {
  /**
   * Finds an article by its ID.
   *
   * @param id the article ID
   * @return the article data, or null if not found
   */
  ArticleData findById(@Param("id") String id);

  /**
   * Finds an article by its slug.
   *
   * @param slug the article slug
   * @return the article data, or null if not found
   */
  ArticleData findBySlug(@Param("slug") String slug);

  /**
   * Queries article IDs with filters and offset-based pagination.
   *
   * @param tag filter by tag name (optional)
   * @param author filter by author username (optional)
   * @param favoritedBy filter by user who favorited (optional)
   * @param page pagination parameters
   * @return a list of article IDs matching the criteria
   */
  List<String> queryArticles(
      @Param("tag") String tag,
      @Param("author") String author,
      @Param("favoritedBy") String favoritedBy,
      @Param("page") Page page);

  /**
   * Counts articles matching the given filters.
   *
   * @param tag filter by tag name (optional)
   * @param author filter by author username (optional)
   * @param favoritedBy filter by user who favorited (optional)
   * @return the count of matching articles
   */
  int countArticle(
      @Param("tag") String tag,
      @Param("author") String author,
      @Param("favoritedBy") String favoritedBy);

  /**
   * Finds articles by their IDs.
   *
   * @param articleIds the list of article IDs
   * @return a list of article data
   */
  List<ArticleData> findArticles(@Param("articleIds") List<String> articleIds);

  /**
   * Finds articles by authors with offset-based pagination.
   *
   * @param authors the list of author user IDs
   * @param page pagination parameters
   * @return a list of article data
   */
  List<ArticleData> findArticlesOfAuthors(
      @Param("authors") List<String> authors, @Param("page") Page page);

  /**
   * Finds articles by authors with cursor-based pagination.
   *
   * @param authors the list of author user IDs
   * @param page cursor pagination parameters
   * @return a list of article data
   */
  List<ArticleData> findArticlesOfAuthorsWithCursor(
      @Param("authors") List<String> authors, @Param("page") CursorPageParameter page);

  /**
   * Counts the total feed size for the given authors.
   *
   * @param authors the list of author user IDs
   * @return the total count of articles
   */
  int countFeedSize(@Param("authors") List<String> authors);

  /**
   * Queries article IDs with filters and cursor-based pagination.
   *
   * @param tag filter by tag name (optional)
   * @param author filter by author username (optional)
   * @param favoritedBy filter by user who favorited (optional)
   * @param page cursor pagination parameters
   * @return a list of article IDs matching the criteria
   */
  List<String> findArticlesWithCursor(
      @Param("tag") String tag,
      @Param("author") String author,
      @Param("favoritedBy") String favoritedBy,
      @Param("page") CursorPageParameter page);
}
