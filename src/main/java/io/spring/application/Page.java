package io.spring.application;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Represents pagination parameters for offset-based pagination.
 *
 * <p>This class encapsulates the offset and limit values used for traditional
 * offset-based pagination. It includes validation to ensure values are within
 * acceptable ranges.
 *
 * <p>Default values:
 * <ul>
 *   <li>offset: 0 (start from the beginning)</li>
 *   <li>limit: 20 (return 20 items per page)</li>
 * </ul>
 *
 * <p>The maximum limit is capped at 100 to prevent excessive data retrieval.
 *
 * @see CursorPageParameter
 */
@NoArgsConstructor
@Data
public class Page {
  /** Maximum allowed limit to prevent excessive data retrieval. */
  private static final int MAX_LIMIT = 100;

  /** The number of items to skip from the beginning. */
  private int offset = 0;

  /** The maximum number of items to return. */
  private int limit = 20;

  /**
   * Creates a new Page with the specified offset and limit.
   *
   * <p>Values are validated and adjusted if necessary:
   * <ul>
   *   <li>Negative offsets are ignored (defaults to 0)</li>
   *   <li>Limits exceeding MAX_LIMIT are capped at MAX_LIMIT</li>
   *   <li>Non-positive limits are ignored (defaults to 20)</li>
   * </ul>
   *
   * @param offset the number of items to skip
   * @param limit the maximum number of items to return
   */
  public Page(int offset, int limit) {
    setOffset(offset);
    setLimit(limit);
  }

  /**
   * Sets the offset value with validation.
   *
   * @param offset the offset value (must be positive to take effect)
   */
  private void setOffset(int offset) {
    if (offset > 0) {
      this.offset = offset;
    }
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
