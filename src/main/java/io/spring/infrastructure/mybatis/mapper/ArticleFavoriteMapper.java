package io.spring.infrastructure.mybatis.mapper;

import io.spring.core.favorite.ArticleFavorite;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis mapper interface for article favorite operations.
 *
 * <p>This mapper provides database operations for managing article favorites,
 * including finding, inserting, and deleting favorite relationships.
 *
 * @see ArticleFavorite
 */
@Mapper
public interface ArticleFavoriteMapper {
  /**
   * Finds a favorite relationship between an article and a user.
   *
   * @param articleId the article ID
   * @param userId the user ID
   * @return the favorite relationship, or null if not found
   */
  ArticleFavorite find(@Param("articleId") String articleId, @Param("userId") String userId);

  /**
   * Inserts a new article favorite relationship.
   *
   * @param articleFavorite the favorite relationship to insert
   */
  void insert(@Param("articleFavorite") ArticleFavorite articleFavorite);

  /**
   * Deletes an article favorite relationship.
   *
   * @param favorite the favorite relationship to delete
   */
  void delete(@Param("favorite") ArticleFavorite favorite);
}
