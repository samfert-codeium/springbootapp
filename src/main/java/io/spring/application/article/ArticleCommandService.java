package io.spring.application.article;

import io.spring.core.article.Article;
import io.spring.core.article.ArticleRepository;
import io.spring.core.user.User;
import javax.validation.Valid;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.annotation.Validated;

/**
 * Service class for article write operations following the CQRS pattern.
 *
 * <p>This service handles the command side of article operations, including
 * creating new articles and updating existing ones. It validates input parameters
 * using Bean Validation annotations.
 *
 * @see io.spring.application.ArticleQueryService
 * @see Article
 */
@Service
@Validated
@AllArgsConstructor
public class ArticleCommandService {

  /** Repository for persisting article entities. */
  private ArticleRepository articleRepository;

  /**
   * Creates a new article.
   *
   * <p>The article title is validated to ensure it's not blank and doesn't
   * duplicate an existing article's slug.
   *
   * @param newArticleParam the parameters for the new article
   * @param creator the user creating the article
   * @return the newly created article
   */
  public Article createArticle(@Valid NewArticleParam newArticleParam, User creator) {
    Article article =
        new Article(
            newArticleParam.getTitle(),
            newArticleParam.getDescription(),
            newArticleParam.getBody(),
            newArticleParam.getTagList(),
            creator.getId());
    articleRepository.save(article);
    return article;
  }

  /**
   * Updates an existing article.
   *
   * <p>Only non-empty fields in the update parameters will be applied to the article.
   *
   * @param article the article to update
   * @param updateArticleParam the parameters for updating the article
   * @return the updated article
   */
  public Article updateArticle(Article article, @Valid UpdateArticleParam updateArticleParam) {
    article.update(
        updateArticleParam.getTitle(),
        updateArticleParam.getDescription(),
        updateArticleParam.getBody());
    articleRepository.save(article);
    return article;
  }
}
