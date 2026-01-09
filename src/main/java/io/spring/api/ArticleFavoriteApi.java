package io.spring.api;

import io.spring.api.exception.ResourceNotFoundException;
import io.spring.application.ArticleQueryService;
import io.spring.application.data.ArticleData;
import io.spring.core.article.Article;
import io.spring.core.article.ArticleRepository;
import io.spring.core.favorite.ArticleFavorite;
import io.spring.core.favorite.ArticleFavoriteRepository;
import io.spring.core.user.User;
import java.util.HashMap;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for article favorite operations.
 *
 * <p>This controller handles favoriting and unfavoriting articles.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>POST /articles/{slug}/favorite - Favorite an article</li>
 *   <li>DELETE /articles/{slug}/favorite - Unfavorite an article</li>
 * </ul>
 *
 * @see ArticleFavorite
 * @see Article
 */
@RestController
@RequestMapping(path = "articles/{slug}/favorite")
@AllArgsConstructor
public class ArticleFavoriteApi {
  /** Repository for article favorite persistence. */
  private ArticleFavoriteRepository articleFavoriteRepository;

  /** Repository for article persistence. */
  private ArticleRepository articleRepository;

  /** Service for querying article data. */
  private ArticleQueryService articleQueryService;

  /**
   * Favorites an article for the authenticated user.
   *
   * @param slug the URL-friendly slug of the article
   * @param user the authenticated user
   * @return the article data with updated favorite status
   * @throws ResourceNotFoundException if the article is not found
   */
  @PostMapping
  public ResponseEntity favoriteArticle(
      @PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
    Article article =
        articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
    ArticleFavorite articleFavorite = new ArticleFavorite(article.getId(), user.getId());
    articleFavoriteRepository.save(articleFavorite);
    return responseArticleData(articleQueryService.findBySlug(slug, user).get());
  }

  /**
   * Unfavorites an article for the authenticated user.
   *
   * @param slug the URL-friendly slug of the article
   * @param user the authenticated user
   * @return the article data with updated favorite status
   * @throws ResourceNotFoundException if the article is not found
   */
  @DeleteMapping
  public ResponseEntity unfavoriteArticle(
      @PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
    Article article =
        articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
    articleFavoriteRepository
        .find(article.getId(), user.getId())
        .ifPresent(
            favorite -> {
              articleFavoriteRepository.remove(favorite);
            });
    return responseArticleData(articleQueryService.findBySlug(slug, user).get());
  }

  /**
   * Wraps article data in a response entity.
   *
   * @param articleData the article data to wrap
   * @return a response entity containing the article data
   */
  private ResponseEntity<HashMap<String, Object>> responseArticleData(
      final ArticleData articleData) {
    return ResponseEntity.ok(
        new HashMap<String, Object>() {
          {
            put("article", articleData);
          }
        });
  }
}
