package io.spring.application;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;

/**
 * A cursor implementation for DateTime-based pagination.
 *
 * <p>This cursor uses Joda-Time DateTime values to mark positions in datasets
 * that are ordered by timestamp. The cursor is serialized as milliseconds since
 * epoch for efficient storage and transmission.
 *
 * @see PageCursor
 * @see CursorPager
 */
public class DateTimeCursor extends PageCursor<DateTime> {

  /**
   * Creates a new DateTimeCursor with the specified DateTime.
   *
   * @param data the DateTime value for the cursor
   */
  public DateTimeCursor(DateTime data) {
    super(data);
  }

  /**
   * Returns the string representation of this cursor.
   *
   * <p>The DateTime is serialized as milliseconds since epoch for efficient
   * storage and transmission.
   *
   * @return the milliseconds since epoch as a string
   */
  @Override
  public String toString() {
    return String.valueOf(getData().getMillis());
  }

  /**
   * Parses a cursor string into a DateTime.
   *
   * <p>The cursor string should be milliseconds since epoch. The resulting
   * DateTime is set to UTC timezone.
   *
   * @param cursor the cursor string to parse, or null
   * @return the parsed DateTime in UTC, or null if the cursor is null
   */
  public static DateTime parse(String cursor) {
    if (cursor == null) {
      return null;
    }
    return new DateTime().withMillis(Long.parseLong(cursor)).withZone(DateTimeZone.UTC);
  }
}
