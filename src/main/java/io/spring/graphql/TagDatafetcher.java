package io.spring.graphql;

import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import io.spring.application.TagsQueryService;
import io.spring.graphql.DgsConstants.QUERY;
import java.util.List;
import lombok.AllArgsConstructor;

/**
 * GraphQL data fetcher for tag queries.
 *
 * <p>This component handles GraphQL queries related to tags:
 * <ul>
 *   <li>tags - Get all available tags</li>
 * </ul>
 *
 * @see TagsQueryService
 */
@DgsComponent
@AllArgsConstructor
public class TagDatafetcher {
  /** Service for querying tag data. */
  private TagsQueryService tagsQueryService;

  /**
   * Retrieves all available tags.
   *
   * @return a list of all tag names
   */
  @DgsData(parentType = DgsConstants.QUERY_TYPE, field = QUERY.Tags)
  public List<String> getTags() {
    return tagsQueryService.allTags();
  }
}
