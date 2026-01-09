package io.spring.api;

import com.fasterxml.jackson.annotation.JsonRootName;
import io.spring.api.exception.NoAuthorizationException;
import io.spring.api.exception.ResourceNotFoundException;
import io.spring.application.CommentQueryService;
import io.spring.application.data.CommentData;
import io.spring.core.article.Article;
import io.spring.core.article.ArticleRepository;
import io.spring.core.comment.Comment;
import io.spring.core.comment.CommentRepository;
import io.spring.core.service.AuthorizationService;
import io.spring.core.user.User;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

/**
 * REST API controller for comment operations on articles.
 *
 * <p>This controller handles creating, listing, and deleting comments on articles.
 *
 * <p>Endpoints:
 * <ul>
 *   <li>POST /articles/{slug}/comments - Create a comment</li>
 *   <li>GET /articles/{slug}/comments - List comments on an article</li>
 *   <li>DELETE /articles/{slug}/comments/{id} - Delete a comment</li>
 * </ul>
 *
 * @see Comment
 * @see Article
 */
@RestController
@RequestMapping(path = "/articles/{slug}/comments")
@AllArgsConstructor
public class CommentsApi {
  /** Repository for article persistence. */
  private ArticleRepository articleRepository;

  /** Repository for comment persistence. */
  private CommentRepository commentRepository;

  /** Service for querying comment data. */
  private CommentQueryService commentQueryService;

  /**
   * Creates a new comment on an article.
   *
   * @param slug the URL-friendly slug of the article
   * @param user the authenticated user
   * @param newCommentParam the comment content
   * @return the created comment data with 201 status
   * @throws ResourceNotFoundException if the article is not found
   */
  @PostMapping
  public ResponseEntity<?> createComment(
      @PathVariable("slug") String slug,
      @AuthenticationPrincipal User user,
      @Valid @RequestBody NewCommentParam newCommentParam) {
    Article article =
        articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
    Comment comment = new Comment(newCommentParam.getBody(), user.getId(), article.getId());
    commentRepository.save(comment);
    return ResponseEntity.status(201)
        .body(commentResponse(commentQueryService.findById(comment.getId(), user).get()));
  }

  /**
   * Retrieves all comments on an article.
   *
   * @param slug the URL-friendly slug of the article
   * @param user the authenticated user (optional)
   * @return a list of comments on the article
   * @throws ResourceNotFoundException if the article is not found
   */
  @GetMapping
  public ResponseEntity getComments(
      @PathVariable("slug") String slug, @AuthenticationPrincipal User user) {
    Article article =
        articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
    List<CommentData> comments = commentQueryService.findByArticleId(article.getId(), user);
    return ResponseEntity.ok(
        new HashMap<String, Object>() {
          {
            put("comments", comments);
          }
        });
  }

  /**
   * Deletes a comment from an article.
   *
   * <p>Only the comment author or article author can delete the comment.
   *
   * @param slug the URL-friendly slug of the article
   * @param commentId the ID of the comment to delete
   * @param user the authenticated user
   * @return 204 No Content on success
   * @throws ResourceNotFoundException if the article or comment is not found
   * @throws NoAuthorizationException if the user is not authorized
   */
  @RequestMapping(path = "{id}", method = RequestMethod.DELETE)
  public ResponseEntity deleteComment(
      @PathVariable("slug") String slug,
      @PathVariable("id") String commentId,
      @AuthenticationPrincipal User user) {
    Article article =
        articleRepository.findBySlug(slug).orElseThrow(ResourceNotFoundException::new);
    return commentRepository
        .findById(article.getId(), commentId)
        .map(
            comment -> {
              if (!AuthorizationService.canWriteComment(user, article, comment)) {
                throw new NoAuthorizationException();
              }
              commentRepository.remove(comment);
              return ResponseEntity.noContent().build();
            })
        .orElseThrow(ResourceNotFoundException::new);
  }

  /**
   * Wraps comment data in a response map.
   *
   * @param commentData the comment data to wrap
   * @return a map containing the comment data
   */
  private Map<String, Object> commentResponse(CommentData commentData) {
    return new HashMap<String, Object>() {
      {
        put("comment", commentData);
      }
    };
  }
}

/**
 * Parameter object for creating a new comment.
 *
 * @see CommentsApi#createComment(String, User, NewCommentParam)
 */
@Getter
@NoArgsConstructor
@JsonRootName("comment")
class NewCommentParam {
  /** The content of the comment. Must not be blank. */
  @NotBlank(message = "can't be empty")
  private String body;
}
