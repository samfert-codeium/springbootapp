package io.spring.core.service;

import io.spring.core.article.Article;
import io.spring.core.comment.Comment;
import io.spring.core.user.User;

/**
 * Service class for authorization checks in the RealWorld application.
 *
 * <p>This class provides static utility methods to determine whether a user
 * has permission to perform certain actions on articles and comments.
 * It implements the authorization rules for the application's domain logic.
 *
 * <p>Authorization rules:
 * <ul>
 *   <li>Only the author of an article can modify or delete it</li>
 *   <li>Both the article author and comment author can delete a comment</li>
 * </ul>
 *
 * @see Article
 * @see Comment
 * @see User
 */
public class AuthorizationService {
  /**
   * Checks if a user has permission to modify or delete an article.
   *
   * <p>Only the author of the article (the user whose ID matches the article's
   * userId) is authorized to write (modify or delete) the article.
   *
   * @param user the user attempting to modify the article
   * @param article the article to be modified
   * @return {@code true} if the user is the author of the article, {@code false} otherwise
   */
  public static boolean canWriteArticle(User user, Article article) {
    return user.getId().equals(article.getUserId());
  }

  /**
   * Checks if a user has permission to delete a comment.
   *
   * <p>A user can delete a comment if they are either:
   * <ul>
   *   <li>The author of the article containing the comment</li>
   *   <li>The author of the comment itself</li>
   * </ul>
   *
   * @param user the user attempting to delete the comment
   * @param article the article containing the comment
   * @param comment the comment to be deleted
   * @return {@code true} if the user is authorized to delete the comment, {@code false} otherwise
   */
  public static boolean canWriteComment(User user, Article article, Comment comment) {
    return user.getId().equals(article.getUserId()) || user.getId().equals(comment.getUserId());
  }
}
