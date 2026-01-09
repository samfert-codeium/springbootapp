package io.spring.application;

import java.util.List;
import lombok.Getter;

/**
 * A generic cursor-based pagination container.
 *
 * <p>This class implements cursor-based pagination (also known as keyset pagination),
 * which is more efficient than offset-based pagination for large datasets. It provides
 * information about whether there are more pages available in either direction.
 *
 * <p>Cursor-based pagination uses a cursor (typically a timestamp or ID) to mark
 * the position in the dataset, allowing for efficient navigation without the
 * performance issues of large offsets.
 *
 * @param <T> the type of elements in the page, must extend {@link Node}
 * @see Node
 * @see PageCursor
 * @see CursorPageParameter
 */
@Getter
public class CursorPager<T extends Node> {
  /** The list of items in the current page. */
  private List<T> data;

  /** Whether there are more items after the current page. */
  private boolean next;

  /** Whether there are more items before the current page. */
  private boolean previous;

  /**
   * Creates a new CursorPager with the specified data and pagination state.
   *
   * <p>The hasExtra parameter indicates whether there are more items beyond
   * the current page in the direction of navigation.
   *
   * @param data the list of items in the current page
   * @param direction the direction of pagination (NEXT or PREV)
   * @param hasExtra whether there are more items in the navigation direction
   */
  public CursorPager(List<T> data, Direction direction, boolean hasExtra) {
    this.data = data;

    if (direction == Direction.NEXT) {
      this.previous = false;
      this.next = hasExtra;
    } else {
      this.next = false;
      this.previous = hasExtra;
    }
  }

  /**
   * Checks if there are more items after the current page.
   *
   * @return {@code true} if there are more items, {@code false} otherwise
   */
  public boolean hasNext() {
    return next;
  }

  /**
   * Checks if there are more items before the current page.
   *
   * @return {@code true} if there are more items, {@code false} otherwise
   */
  public boolean hasPrevious() {
    return previous;
  }

  /**
   * Gets the cursor for the first item in the current page.
   *
   * @return the start cursor, or null if the page is empty
   */
  public PageCursor getStartCursor() {
    return data.isEmpty() ? null : data.get(0).getCursor();
  }

  /**
   * Gets the cursor for the last item in the current page.
   *
   * @return the end cursor, or null if the page is empty
   */
  public PageCursor getEndCursor() {
    return data.isEmpty() ? null : data.get(data.size() - 1).getCursor();
  }

  /**
   * Enumeration of pagination directions.
   */
  public enum Direction {
    /** Navigate to previous items. */
    PREV,
    /** Navigate to next items. */
    NEXT
  }
}
