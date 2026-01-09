package io.spring.application;

import static java.util.stream.Collectors.toList;

import io.spring.application.data.ArticleData;
import io.spring.application.data.ArticleDataList;
import io.spring.application.data.ArticleFavoriteCount;
import io.spring.core.user.User;
import io.spring.infrastructure.mybatis.readservice.ArticleFavoritesReadService;
import io.spring.infrastructure.mybatis.readservice.ArticleReadService;
import io.spring.infrastructure.mybatis.readservice.UserRelationshipQueryService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * Service class for querying article data following the CQRS pattern.
 *
 * <p>This service is responsible for reading article data and enriching it with
 * additional information such as favorite counts, whether the current user has
 * favorited the article, and whether the current user is following the author.
 *
 * <p>The service supports both offset-based pagination and cursor-based pagination
 * for efficient data retrieval in different scenarios.
 *
 * @see ArticleData
 * @see ArticleDataList
 * @see CursorPager
 */
@Service
@AllArgsConstructor
public class ArticleQueryService {
  /** Service for reading article data from the database. */
  private ArticleReadService articleReadService;

  /** Service for querying user relationship data. */
  private UserRelationshipQueryService userRelationshipQueryService;

  /** Service for reading article favorite data. */
  private ArticleFavoritesReadService articleFavoritesReadService;

  /**
   * Finds an article by its unique identifier.
   *
   * <p>If a user is provided, the article data is enriched with user-specific
   * information such as whether the user has favorited the article and whether
   * the user is following the author.
   *
   * @param id the unique identifier of the article
   * @param user the current user, or null if not authenticated
   * @return an Optional containing the article data if found, or empty if not found
   */
  public Optional<ArticleData> findById(String id, User user) {
    ArticleData articleData = articleReadService.findById(id);
    if (articleData == null) {
      return Optional.empty();
    } else {
      if (user != null) {
        fillExtraInfo(id, user, articleData);
      }
      return Optional.of(articleData);
    }
  }

  /**
   * Finds an article by its URL-friendly slug.
   *
   * <p>If a user is provided, the article data is enriched with user-specific
   * information such as whether the user has favorited the article and whether
   * the user is following the author.
   *
   * @param slug the URL-friendly slug of the article
   * @param user the current user, or null if not authenticated
   * @return an Optional containing the article data if found, or empty if not found
   */
  public Optional<ArticleData> findBySlug(String slug, User user) {
    ArticleData articleData = articleReadService.findBySlug(slug);
    if (articleData == null) {
      return Optional.empty();
    } else {
      if (user != null) {
        fillExtraInfo(articleData.getId(), user, articleData);
      }
      return Optional.of(articleData);
    }
  }

  /**
   * Finds recent articles with cursor-based pagination.
   *
   * <p>This method supports filtering by tag, author, and favorited-by user.
   * Results are paginated using cursor-based pagination for efficient scrolling.
   *
   * @param tag filter by tag name, or null for no tag filter
   * @param author filter by author username, or null for no author filter
   * @param favoritedBy filter by user who favorited, or null for no favorite filter
   * @param page cursor pagination parameters
   * @param currentUser the current user for enriching data, or null if not authenticated
   * @return a CursorPager containing the matching articles
   */
  public CursorPager<ArticleData> findRecentArticlesWithCursor(
      String tag,
      String author,
      String favoritedBy,
      CursorPageParameter<DateTime> page,
      User currentUser) {
    List<String> articleIds =
        articleReadService.findArticlesWithCursor(tag, author, favoritedBy, page);
    if (articleIds.size() == 0) {
      return new CursorPager<>(new ArrayList<>(), page.getDirection(), false);
    } else {
      boolean hasExtra = articleIds.size() > page.getLimit();
      if (hasExtra) {
        articleIds.remove(page.getLimit());
      }
      if (!page.isNext()) {
        Collections.reverse(articleIds);
      }

      List<ArticleData> articles = articleReadService.findArticles(articleIds);
      fillExtraInfo(articles, currentUser);

      return new CursorPager<>(articles, page.getDirection(), hasExtra);
    }
  }

  /**
   * Finds articles from users that the current user follows with cursor-based pagination.
   *
   * <p>This method returns articles only from authors that the specified user is following,
   * providing a personalized feed experience.
   *
   * @param user the current user whose feed is being retrieved
   * @param page cursor pagination parameters
   * @return a CursorPager containing articles from followed users
   */
  public CursorPager<ArticleData> findUserFeedWithCursor(
      User user, CursorPageParameter<DateTime> page) {
    List<String> followdUsers = userRelationshipQueryService.followedUsers(user.getId());
    if (followdUsers.size() == 0) {
      return new CursorPager<>(new ArrayList<>(), page.getDirection(), false);
    } else {
      List<ArticleData> articles =
          articleReadService.findArticlesOfAuthorsWithCursor(followdUsers, page);
      boolean hasExtra = articles.size() > page.getLimit();
      if (hasExtra) {
        articles.remove(page.getLimit());
      }
      if (!page.isNext()) {
        Collections.reverse(articles);
      }
      fillExtraInfo(articles, user);
      return new CursorPager<>(articles, page.getDirection(), hasExtra);
    }
  }

  /**
   * Finds recent articles with offset-based pagination.
   *
   * <p>This method supports filtering by tag, author, and favorited-by user.
   * Results are paginated using traditional offset-based pagination.
   *
   * @param tag filter by tag name, or null for no tag filter
   * @param author filter by author username, or null for no author filter
   * @param favoritedBy filter by user who favorited, or null for no favorite filter
   * @param page offset pagination parameters
   * @param currentUser the current user for enriching data, or null if not authenticated
   * @return an ArticleDataList containing the matching articles and total count
   */
  public ArticleDataList findRecentArticles(
      String tag, String author, String favoritedBy, Page page, User currentUser) {
    List<String> articleIds = articleReadService.queryArticles(tag, author, favoritedBy, page);
    int articleCount = articleReadService.countArticle(tag, author, favoritedBy);
    if (articleIds.size() == 0) {
      return new ArticleDataList(new ArrayList<>(), articleCount);
    } else {
      List<ArticleData> articles = articleReadService.findArticles(articleIds);
      fillExtraInfo(articles, currentUser);
      return new ArticleDataList(articles, articleCount);
    }
  }

  /**
   * Finds articles from users that the current user follows with offset-based pagination.
   *
   * <p>This method returns articles only from authors that the specified user is following,
   * providing a personalized feed experience with traditional pagination.
   *
   * @param user the current user whose feed is being retrieved
   * @param page offset pagination parameters
   * @return an ArticleDataList containing articles from followed users and total count
   */
  public ArticleDataList findUserFeed(User user, Page page) {
    List<String> followdUsers = userRelationshipQueryService.followedUsers(user.getId());
    if (followdUsers.size() == 0) {
      return new ArticleDataList(new ArrayList<>(), 0);
    } else {
      List<ArticleData> articles = articleReadService.findArticlesOfAuthors(followdUsers, page);
      fillExtraInfo(articles, user);
      int count = articleReadService.countFeedSize(followdUsers);
      return new ArticleDataList(articles, count);
    }
  }

  /**
   * Enriches a list of articles with additional information.
   *
   * <p>Sets favorite counts for all articles, and if a user is provided,
   * also sets whether each article is favorited by the user and whether
   * the user is following each article's author.
   *
   * @param articles the list of articles to enrich
   * @param currentUser the current user, or null if not authenticated
   */
  private void fillExtraInfo(List<ArticleData> articles, User currentUser) {
    setFavoriteCount(articles);
    if (currentUser != null) {
      setIsFavorite(articles, currentUser);
      setIsFollowingAuthor(articles, currentUser);
    }
  }

  /**
   * Sets the following status for each article's author.
   *
   * <p>Checks which authors the current user is following and updates
   * the profile data accordingly.
   *
   * @param articles the list of articles to update
   * @param currentUser the current user
   */
  private void setIsFollowingAuthor(List<ArticleData> articles, User currentUser) {
    Set<String> followingAuthors =
        userRelationshipQueryService.followingAuthors(
            currentUser.getId(),
            articles.stream()
                .map(articleData1 -> articleData1.getProfileData().getId())
                .collect(toList()));
    articles.forEach(
        articleData -> {
          if (followingAuthors.contains(articleData.getProfileData().getId())) {
            articleData.getProfileData().setFollowing(true);
          }
        });
  }

  /**
   * Sets the favorite count for each article.
   *
   * <p>Retrieves the favorite counts for all articles in a single batch query
   * and updates each article's favorite count.
   *
   * @param articles the list of articles to update
   */
  private void setFavoriteCount(List<ArticleData> articles) {
    List<ArticleFavoriteCount> favoritesCounts =
        articleFavoritesReadService.articlesFavoriteCount(
            articles.stream().map(ArticleData::getId).collect(toList()));
    Map<String, Integer> countMap = new HashMap<>();
    favoritesCounts.forEach(
        item -> {
          countMap.put(item.getId(), item.getCount());
        });
    articles.forEach(
        articleData -> articleData.setFavoritesCount(countMap.get(articleData.getId())));
  }

  /**
   * Sets the favorited status for each article based on the current user.
   *
   * <p>Checks which articles the current user has favorited and updates
   * the favorited flag accordingly.
   *
   * @param articles the list of articles to update
   * @param currentUser the current user
   */
  private void setIsFavorite(List<ArticleData> articles, User currentUser) {
    Set<String> favoritedArticles =
        articleFavoritesReadService.userFavorites(
            articles.stream().map(articleData -> articleData.getId()).collect(toList()),
            currentUser);

    articles.forEach(
        articleData -> {
          if (favoritedArticles.contains(articleData.getId())) {
            articleData.setFavorited(true);
          }
        });
  }

  /**
   * Enriches a single article with user-specific information.
   *
   * <p>Sets whether the user has favorited the article, the total favorite count,
   * and whether the user is following the article's author.
   *
   * @param id the article ID
   * @param user the current user
   * @param articleData the article data to enrich
   */
  private void fillExtraInfo(String id, User user, ArticleData articleData) {
    articleData.setFavorited(articleFavoritesReadService.isUserFavorite(user.getId(), id));
    articleData.setFavoritesCount(articleFavoritesReadService.articleFavoriteCount(id));
    articleData
        .getProfileData()
        .setFollowing(
            userRelationshipQueryService.isUserFollowing(
                user.getId(), articleData.getProfileData().getId()));
  }
}
