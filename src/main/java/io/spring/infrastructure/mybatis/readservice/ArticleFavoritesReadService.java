package io.spring.infrastructure.mybatis.readservice;

import io.spring.application.data.ArticleFavoriteCount;
import io.spring.core.user.User;
import java.util.List;
import java.util.Set;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

/**
 * MyBatis read service for article favorites queries.
 *
 * <p>This service provides read-only queries for article favorite data,
 * optimized for the CQRS read model.
 *
 * @see ArticleFavoriteCount
 */
@Mapper
public interface ArticleFavoritesReadService {
  /**
   * Checks if a user has favorited an article.
   *
   * @param userId the user ID
   * @param articleId the article ID
   * @return true if the user has favorited the article
   */
  boolean isUserFavorite(@Param("userId") String userId, @Param("articleId") String articleId);

  /**
   * Gets the favorite count for an article.
   *
   * @param articleId the article ID
   * @return the number of users who have favorited the article
   */
  int articleFavoriteCount(@Param("articleId") String articleId);

  /**
   * Gets favorite counts for multiple articles.
   *
   * @param ids the list of article IDs
   * @return a list of favorite counts for each article
   */
  List<ArticleFavoriteCount> articlesFavoriteCount(@Param("ids") List<String> ids);

  /**
   * Gets the set of article IDs that a user has favorited.
   *
   * @param ids the list of article IDs to check
   * @param currentUser the current user
   * @return a set of article IDs that the user has favorited
   */
  Set<String> userFavorites(@Param("ids") List<String> ids, @Param("currentUser") User currentUser);
}
