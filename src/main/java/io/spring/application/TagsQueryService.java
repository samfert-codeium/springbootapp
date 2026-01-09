package io.spring.application;

import io.spring.infrastructure.mybatis.readservice.TagReadService;
import java.util.List;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

/**
 * Service class for querying tag data following the CQRS pattern.
 *
 * <p>This service is responsible for reading tag data from the database.
 * It provides a simple interface for retrieving all available tags.
 *
 * @see io.spring.core.article.Tag
 */
@Service
@AllArgsConstructor
public class TagsQueryService {
  /** Service for reading tag data from the database. */
  private TagReadService tagReadService;

  /**
   * Retrieves all available tags.
   *
   * @return a list of all tag names
   */
  public List<String> allTags() {
    return tagReadService.all();
  }
}
