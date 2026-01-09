package io.spring.application.data;

import com.fasterxml.jackson.annotation.JsonProperty;
import java.util.List;
import lombok.Getter;

/**
 * Data Transfer Object (DTO) for a paginated list of articles.
 *
 * <p>This class wraps a list of articles along with the total count,
 * used for offset-based pagination responses in the REST API.
 *
 * @see ArticleData
 */
@Getter
public class ArticleDataList {
  /** The list of articles in the current page. */
  @JsonProperty("articles")
  private final List<ArticleData> articleDatas;

  /** The total count of articles matching the query. */
  @JsonProperty("articlesCount")
  private final int count;

  /**
   * Creates a new ArticleDataList with the specified articles and count.
   *
   * @param articleDatas the list of articles in the current page
   * @param count the total count of articles matching the query
   */
  public ArticleDataList(List<ArticleData> articleDatas, int count) {

    this.articleDatas = articleDatas;
    this.count = count;
  }
}
