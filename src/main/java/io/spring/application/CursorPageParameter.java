package io.spring.application;

import io.spring.application.CursorPager.Direction;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents pagination parameters for cursor-based pagination.
 *
 * <p>This class encapsulates the cursor, limit, and direction values used for
 * cursor-based pagination. It includes validation to ensure values are within
 * acceptable ranges.
 *
 * <p>Default values:
 * <ul>
 *   <li>limit: 20 (return 20 items per page)</li>
 *   <li>cursor: null (start from the beginning)</li>
 * </ul>
 *
 * <p>The maximum limit is capped at 1000 to prevent excessive data retrieval.
 *
 * @param <T> the type of the cursor value (typically DateTime or String)
 * @see CursorPager
 * @see Page
 */
@Data
@NoArgsConstructor
public class CursorPageParameter<T> {
  /** Maximum allowed limit to prevent excessive data retrieval. */
  private static final int MAX_LIMIT = 1000;

  /** The maximum number of items to return. */
  private int limit = 20;

  /** The cursor marking the position in the dataset. */
  private T cursor;

  /** The direction of pagination (NEXT or PREV). */
  private Direction direction;

  /**
   * Creates a new CursorPageParameter with the specified values.
   *
   * <p>The limit is validated and capped at MAX_LIMIT if necessary.
   *
   * @param cursor the cursor marking the position in the dataset
   * @param limit the maximum number of items to return
   * @param direction the direction of pagination
   */
  public CursorPageParameter(T cursor, int limit, Direction direction) {
    setLimit(limit);
    setCursor(cursor);
    setDirection(direction);
  }

  /**
   * Checks if the pagination direction is NEXT.
   *
   * @return {@code true} if navigating forward, {@code false} otherwise
   */
  public boolean isNext() {
    return direction == Direction.NEXT;
  }

  /**
   * Gets the query limit, which is one more than the requested limit.
   *
   * <p>The extra item is used to determine if there are more pages available.
   *
   * @return the limit plus one for pagination detection
   */
  public int getQueryLimit() {
    return limit + 1;
  }

  /**
   * Sets the cursor value.
   *
   * @param cursor the cursor value
   */
  private void setCursor(T cursor) {
    this.cursor = cursor;
  }

  /**
   * Sets the limit value with validation.
   *
   * @param limit the limit value (capped at MAX_LIMIT, must be positive)
   */
  private void setLimit(int limit) {
    if (limit > MAX_LIMIT) {
      this.limit = MAX_LIMIT;
    } else if (limit > 0) {
      this.limit = limit;
    }
  }
}
