package io.spring.api;

import io.spring.application.ArticleQueryService;
import io.spring.application.Page;
import io.spring.application.article.ArticleCommandService;
import io.spring.application.article.NewArticleParam;
import io.spring.core.article.Article;
import io.spring.core.user.User;
import java.util.HashMap;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for article collection operations.
 *
 * <p>This controller handles operations on the articles collection,
 * including creating new articles, listing articles with filters, and
 * retrieving the user's feed.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>POST /articles - Create a new article</li>
 *   <li>GET /articles - List articles with optional filters</li>
 *   <li>GET /articles/feed - Get articles from followed users</li>
 * </ul>
 *
 * @see ArticleApi
 * @see Article
 */
@RestController
@RequestMapping(path = "/articles")
@AllArgsConstructor
public class ArticlesApi {
  /** Service for article write operations. */
  private ArticleCommandService articleCommandService;

  /** Service for querying article data. */
  private ArticleQueryService articleQueryService;

  /**
   * Creates a new article.
   *
   * @param newArticleParam the parameters for the new article
   * @param user the authenticated user (author)
   * @return the created article data
   */
  @PostMapping
  public ResponseEntity createArticle(
      @Valid @RequestBody NewArticleParam newArticleParam, @AuthenticationPrincipal User user) {
    Article article = articleCommandService.createArticle(newArticleParam, user);
    return ResponseEntity.ok(
        new HashMap<String, Object>() {
          {
            put("article", articleQueryService.findById(article.getId(), user).get());
          }
        });
  }

  /**
   * Retrieves the user's feed of articles from followed authors.
   *
   * @param offset the number of articles to skip (default 0)
   * @param limit the maximum number of articles to return (default 20)
   * @param user the authenticated user
   * @return a paginated list of articles from followed users
   */
  @GetMapping(path = "feed")
  public ResponseEntity getFeed(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "20") int limit,
      @AuthenticationPrincipal User user) {
    return ResponseEntity.ok(articleQueryService.findUserFeed(user, new Page(offset, limit)));
  }

  /**
   * Retrieves a list of articles with optional filters.
   *
   * @param offset the number of articles to skip (default 0)
   * @param limit the maximum number of articles to return (default 20)
   * @param tag filter by tag name (optional)
   * @param favoritedBy filter by username who favorited (optional)
   * @param author filter by author username (optional)
   * @param user the authenticated user (optional)
   * @return a paginated list of articles matching the filters
   */
  @GetMapping
  public ResponseEntity getArticles(
      @RequestParam(value = "offset", defaultValue = "0") int offset,
      @RequestParam(value = "limit", defaultValue = "20") int limit,
      @RequestParam(value = "tag", required = false) String tag,
      @RequestParam(value = "favorited", required = false) String favoritedBy,
      @RequestParam(value = "author", required = false) String author,
      @AuthenticationPrincipal User user) {
    return ResponseEntity.ok(
        articleQueryService.findRecentArticles(
            tag, author, favoritedBy, new Page(offset, limit), user));
  }
}
