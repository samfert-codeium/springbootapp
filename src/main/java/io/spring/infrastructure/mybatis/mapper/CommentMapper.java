package io.spring.infrastructure.mybatis.mapper;

import io.spring.core.comment.Comment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis mapper interface for comment operations.
 *
 * <p>This mapper provides database operations for comments,
 * including inserting, finding, and deleting comments.
 *
 * @see Comment
 */
@Mapper
public interface CommentMapper {
  /**
   * Inserts a new comment into the database.
   *
   * @param comment the comment to insert
   */
  void insert(@Param("comment") Comment comment);

  /**
   * Finds a comment by article ID and comment ID.
   *
   * @param articleId the article ID
   * @param id the comment ID
   * @return the comment, or null if not found
   */
  Comment findById(@Param("articleId") String articleId, @Param("id") String id);

  /**
   * Deletes a comment by its ID.
   *
   * @param id the comment ID
   */
  void delete(@Param("id") String id);
}
