package io.spring.application;

import io.spring.application.data.CommentData;
import io.spring.core.user.User;
import io.spring.infrastructure.mybatis.readservice.CommentReadService;
import io.spring.infrastructure.mybatis.readservice.UserRelationshipQueryService;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import lombok.AllArgsConstructor;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;

/**
 * Service class for querying comment data following the CQRS pattern.
 *
 * <p>This service is responsible for reading comment data and enriching it with
 * additional information such as whether the current user is following the comment author.
 *
 * <p>The service supports both simple list retrieval and cursor-based pagination
 * for efficient data retrieval in different scenarios.
 *
 * @see CommentData
 * @see CursorPager
 */
@Service
@AllArgsConstructor
public class CommentQueryService {
  /** Service for reading comment data from the database. */
  private CommentReadService commentReadService;

  /** Service for querying user relationship data. */
  private UserRelationshipQueryService userRelationshipQueryService;

  /**
   * Finds a comment by its unique identifier.
   *
   * <p>If a user is provided, the comment data is enriched with information
   * about whether the user is following the comment author.
   *
   * @param id the unique identifier of the comment
   * @param user the current user, or null if not authenticated
   * @return an Optional containing the comment data if found, or empty if not found
   */
  public Optional<CommentData> findById(String id, User user) {
    CommentData commentData = commentReadService.findById(id);
    if (commentData == null) {
      return Optional.empty();
    } else {
      commentData
          .getProfileData()
          .setFollowing(
              userRelationshipQueryService.isUserFollowing(
                  user.getId(), commentData.getProfileData().getId()));
    }
    return Optional.ofNullable(commentData);
  }

  /**
   * Finds all comments for a specific article.
   *
   * <p>If a user is provided, the comment data is enriched with information
   * about whether the user is following each comment author.
   *
   * @param articleId the unique identifier of the article
   * @param user the current user, or null if not authenticated
   * @return a list of comments for the article
   */
  public List<CommentData> findByArticleId(String articleId, User user) {
    List<CommentData> comments = commentReadService.findByArticleId(articleId);
    if (comments.size() > 0 && user != null) {
      Set<String> followingAuthors =
          userRelationshipQueryService.followingAuthors(
              user.getId(),
              comments.stream()
                  .map(commentData -> commentData.getProfileData().getId())
                  .collect(Collectors.toList()));
      comments.forEach(
          commentData -> {
            if (followingAuthors.contains(commentData.getProfileData().getId())) {
              commentData.getProfileData().setFollowing(true);
            }
          });
    }
    return comments;
  }

  /**
   * Finds comments for a specific article with cursor-based pagination.
   *
   * <p>If a user is provided, the comment data is enriched with information
   * about whether the user is following each comment author. Results are
   * paginated using cursor-based pagination for efficient scrolling.
   *
   * @param articleId the unique identifier of the article
   * @param user the current user, or null if not authenticated
   * @param page cursor pagination parameters
   * @return a CursorPager containing the matching comments
   */
  public CursorPager<CommentData> findByArticleIdWithCursor(
      String articleId, User user, CursorPageParameter<DateTime> page) {
    List<CommentData> comments = commentReadService.findByArticleIdWithCursor(articleId, page);
    if (comments.isEmpty()) {
      return new CursorPager<>(new ArrayList<>(), page.getDirection(), false);
    }
    if (user != null) {
      Set<String> followingAuthors =
          userRelationshipQueryService.followingAuthors(
              user.getId(),
              comments.stream()
                  .map(commentData -> commentData.getProfileData().getId())
                  .collect(Collectors.toList()));
      comments.forEach(
          commentData -> {
            if (followingAuthors.contains(commentData.getProfileData().getId())) {
              commentData.getProfileData().setFollowing(true);
            }
          });
    }
    boolean hasExtra = comments.size() > page.getLimit();
    if (hasExtra) {
      comments.remove(page.getLimit());
    }
    if (!page.isNext()) {
      Collections.reverse(comments);
    }
    return new CursorPager<>(comments, page.getDirection(), hasExtra);
  }
}
