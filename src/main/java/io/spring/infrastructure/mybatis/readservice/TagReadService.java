package io.spring.infrastructure.mybatis.readservice;

import java.util.List;
import org.apache.ibatis.annotations.Mapper;

/**
 * MyBatis read service for tag queries.
 *
 * <p>This service provides read-only queries for tag data.
 */
@Mapper
public interface TagReadService {
  /**
   * Retrieves all tag names.
   *
   * @return a list of all tag names
   */
  List<String> all();
}
