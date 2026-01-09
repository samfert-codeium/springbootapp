package io.spring.api;

import io.spring.api.exception.NoAuthorizationException;
import io.spring.api.exception.ResourceNotFoundException;
import io.spring.application.ArticleQueryService;
import io.spring.application.article.ArticleCommandService;
import io.spring.application.article.UpdateArticleParam;
import io.spring.application.data.ArticleData;
import io.spring.core.article.Article;
import io.spring.core.article.ArticleRepository;
import io.spring.core.service.AuthorizationService;
import io.spring.core.user.User;
import java.util.HashMap;
import java.util.Map;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for single article operations.
 *
 * <p>This controller handles operations on individual articles identified by their slug,
 * including retrieving, updating, and deleting articles.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>GET /articles/{slug} - Get an article by slug</li>
 *   <li>PUT /articles/{slug} - Update an article</li>
 *   <li>DELETE /articles/{slug} - Delete an article</li>
 * </ul>
 *
 * @see ArticlesApi
 * @see Article
 */
@RestController
@RequestMapping(path = "/articles/{slug}")
@AllArgsConstructor
public class ArticleApi {
  /** Service for querying article data. */
  private ArticleQueryService articleQueryService;

  /** Repository for article persistence operations. */
  private ArticleRepository articleRepository;

  /** Service for article write operations. */
  private ArticleCommandService articleCommandService;

  /**
   * Retrieves an article by its slug.
   *
   * @param slug the URL-friendly slug of the article
   * @param user the authenticated user (optional)
   * @return the article data wrapped in a response
   * @throws ResourceNotFoundException if the article is not found
   */
  @GetMapping
  public ResponseEntity<?> article(
      @PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
    return articleQueryService
        .findBySlug(slug, user)
        .map(articleData -> ResponseEntity.ok(articleResponse(articleData)))
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Updates an existing article.
   *
   * <p>Only the article's author can update it.
   *
   * @param slug the URL-friendly slug of the article
   * @param user the authenticated user
   * @param updateArticleParam the update parameters
   * @return the updated article data
   * @throws ResourceNotFoundException if the article is not found
   * @throws NoAuthorizationException if the user is not the author
   */
  @PutMapping
  public ResponseEntity<?> updateArticle(
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal User user,
      @Valid @RequestBody UpdateArticleParam updateArticleParam) {
    return articleRepository
        .findBySlug(slug)
        .map(
            article -> {
              if (!AuthorizationService.canWriteArticle(user, article)) {
                throw new NoAuthorizationException();
              }
              Article updatedArticle =
                  articleCommandService.updateArticle(article, updateArticleParam);
              return ResponseEntity.ok(
                  articleResponse(
                      articleQueryService.findBySlug(updatedArticle.getSlug(), user).get()));
            })
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Deletes an article.
   *
   * <p>Only the article's author can delete it.
   *
   * @param slug the URL-friendly slug of the article
   * @param user the authenticated user
   * @return 204 No Content on success
   * @throws ResourceNotFoundException if the article is not found
   * @throws NoAuthorizationException if the user is not the author
   */
  @DeleteMapping
  public ResponseEntity deleteArticle(
      @PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
    return articleRepository
        .findBySlug(slug)
        .map(
            article -> {
              if (!AuthorizationService.canWriteArticle(user, article)) {
                throw new NoAuthorizationException();
              }
              articleRepository.remove(article);
              return ResponseEntity.noContent().build();
            })
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Wraps article data in a response map.
   *
   * @param articleData the article data to wrap
   * @return a map containing the article data
   */
  private Map<String, Object> articleResponse(ArticleData articleData) {
    return new HashMap<String, Object>() {
      {
        put("article", articleData);
      }
    };
  }
}
