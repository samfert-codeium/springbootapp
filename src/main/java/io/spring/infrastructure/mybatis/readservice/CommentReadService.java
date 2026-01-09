package io.spring.infrastructure.mybatis.readservice;

import io.spring.application.CursorPageParameter;
import io.spring.application.data.CommentData;
import java.util.List;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.joda.time.DateTime;

/**
 * MyBatis read service for comment queries.
 *
 * <p>This service provides read-only queries for comment data,
 * supporting both simple and cursor-based pagination.
 *
 * @see CommentData
 */
@Mapper
public interface CommentReadService {
  /**
   * Finds a comment by its ID.
   *
   * @param id the comment ID
   * @return the comment data, or null if not found
   */
  CommentData findById(@Param("id") String id);

  /**
   * Finds all comments for an article.
   *
   * @param articleId the article ID
   * @return a list of comment data
   */
  List<CommentData> findByArticleId(@Param("articleId") String articleId);

  /**
   * Finds comments for an article with cursor-based pagination.
   *
   * @param articleId the article ID
   * @param page cursor pagination parameters
   * @return a list of comment data
   */
  List<CommentData> findByArticleIdWithCursor(
      @Param("articleId") String articleId, @Param("page") CursorPageParameter<DateTime> page);
}
