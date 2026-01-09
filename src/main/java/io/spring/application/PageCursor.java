package io.spring.application;

/**
 * Abstract base class for pagination cursors.
 *
 * <p>A cursor is a marker that identifies a specific position in a dataset,
 * used for cursor-based pagination. Subclasses implement specific cursor types
 * for different data types (e.g., DateTime, String).
 *
 * @param <T> the type of data stored in the cursor
 * @see DateTimeCursor
 * @see CursorPager
 * @see Node
 */
public abstract class PageCursor<T> {
  /** The data value that identifies the position in the dataset. */
  private T data;

  /**
   * Creates a new PageCursor with the specified data.
   *
   * @param data the data value for the cursor
   */
  public PageCursor(T data) {
    this.data = data;
  }

  /**
   * Gets the data value of this cursor.
   *
   * @return the cursor data
   */
  public T getData() {
    return data;
  }

  /**
   * Returns a string representation of this cursor.
   *
   * @return the string representation of the cursor data
   */
  @Override
  public String toString() {
    return data.toString();
  }
}
