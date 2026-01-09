package io.spring.infrastructure.repository;

import io.spring.core.article.Article;
import io.spring.core.article.ArticleRepository;
import io.spring.core.article.Tag;
import io.spring.infrastructure.mybatis.mapper.ArticleMapper;
import java.util.Optional;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 * MyBatis implementation of the ArticleRepository.
 *
 * <p>This repository provides persistence operations for articles and tags
 * using MyBatis as the ORM framework. It handles article creation, updates,
 * and tag relationship management within transactions.
 *
 * @see ArticleRepository
 * @see ArticleMapper
 */
@Repository
public class MyBatisArticleRepository implements ArticleRepository {
  /** MyBatis mapper for article operations. */
  private ArticleMapper articleMapper;

  public MyBatisArticleRepository(ArticleMapper articleMapper) {
    this.articleMapper = articleMapper;
  }

  @Override
  @Transactional
  public void save(Article article) {
    if (articleMapper.findById(article.getId()) == null) {
      createNew(article);
    } else {
      articleMapper.update(article);
    }
  }

  private void createNew(Article article) {
    for (Tag tag : article.getTags()) {
      Tag targetTag =
          Optional.ofNullable(articleMapper.findTag(tag.getName()))
              .orElseGet(
                  () -> {
                    articleMapper.insertTag(tag);
                    return tag;
                  });
      articleMapper.insertArticleTagRelation(article.getId(), targetTag.getId());
    }
    articleMapper.insert(article);
  }

  @Override
  public Optional<Article> findById(String id) {
    return Optional.ofNullable(articleMapper.findById(id));
  }

  @Override
  public Optional<Article> findBySlug(String slug) {
    return Optional.ofNullable(articleMapper.findBySlug(slug));
  }

  @Override
  public void remove(Article article) {
    articleMapper.delete(article.getId());
  }
}
