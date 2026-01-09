package io.spring;

import io.spring.application.data.ArticleData;
import io.spring.application.data.ProfileData;
import io.spring.core.article.Article;
import io.spring.core.user.User;
import java.util.ArrayList;
import java.util.Arrays;
import org.joda.time.DateTime;

/**
 * Helper class for creating test fixtures.
 *
 * <p>Provides utility methods for generating test data objects
 * used across multiple test classes.
 */
public class TestHelper {
  /**
   * Creates an ArticleData fixture with the given seed and user.
   *
   * @param seed a unique seed for generating test data
   * @param user the user to associate as the article author
   * @return an ArticleData instance with generated test values
   */
  public static ArticleData articleDataFixture(String seed, User user) {
    DateTime now = new DateTime();
    return new ArticleData(
        seed + "id",
        "title-" + seed,
        "title " + seed,
        "desc " + seed,
        "body " + seed,
        false,
        0,
        now,
        now,
        new ArrayList<>(),
        new ProfileData(user.getId(), user.getUsername(), user.getBio(), user.getImage(), false));
  }

  /**
   * Creates an ArticleData from an existing Article and User.
   *
   * @param article the article to convert
   * @param user the user to associate as the article author
   * @return an ArticleData instance based on the article and user
   */
  public static ArticleData getArticleDataFromArticleAndUser(Article article, User user) {
    return new ArticleData(
        article.getId(),
        article.getSlug(),
        article.getTitle(),
        article.getDescription(),
        article.getBody(),
        false,
        0,
        article.getCreatedAt(),
        article.getUpdatedAt(),
        Arrays.asList("joda"),
        new ProfileData(user.getId(), user.getUsername(), user.getBio(), user.getImage(), false));
  }
}
