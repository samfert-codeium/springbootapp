package io.spring.application;

/**
 * Interface for entities that support cursor-based pagination.
 *
 * <p>Classes implementing this interface can be used with {@link CursorPager}
 * for cursor-based pagination. Each node must provide a cursor that uniquely
 * identifies its position in the dataset.
 *
 * @see CursorPager
 * @see PageCursor
 */
public interface Node {
  /**
   * Gets the cursor for this node.
   *
   * <p>The cursor is used to identify the position of this item in the dataset
   * for pagination purposes.
   *
   * @return the cursor for this node
   */
  PageCursor getCursor();
}
