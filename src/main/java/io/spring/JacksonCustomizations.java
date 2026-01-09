package io.spring;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.SerializerProvider;
import com.fasterxml.jackson.databind.module.SimpleModule;
import com.fasterxml.jackson.databind.ser.std.StdSerializer;
import java.io.IOException;
import org.joda.time.DateTime;
import org.joda.time.format.ISODateTimeFormat;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Configuration class for customizing Jackson JSON serialization.
 *
 * <p>This class provides custom Jackson modules and serializers to handle
 * specific data types used in the RealWorld application, particularly
 * Joda-Time DateTime objects.
 *
 * @see RealWorldModules
 * @see DateTimeSerializer
 */
@Configuration
public class JacksonCustomizations {

  /**
   * Creates and registers the custom Jackson module for the RealWorld application.
   *
   * @return a Jackson module containing custom serializers
   */
  @Bean
  public Module realWorldModules() {
    return new RealWorldModules();
  }

  /**
   * Custom Jackson module that registers serializers for RealWorld-specific types.
   *
   * <p>This module registers a custom serializer for Joda-Time DateTime objects
   * to ensure consistent date/time formatting across the API.
   */
  public static class RealWorldModules extends SimpleModule {
    /**
     * Creates a new RealWorldModules instance and registers custom serializers.
     */
    public RealWorldModules() {
      addSerializer(DateTime.class, new DateTimeSerializer());
    }
  }

  /**
   * Custom Jackson serializer for Joda-Time DateTime objects.
   *
   * <p>This serializer converts DateTime objects to ISO 8601 formatted strings
   * in UTC timezone, ensuring consistent date/time representation in JSON responses.
   *
   * <p>Example output: "2023-01-15T10:30:00.000Z"
   */
  public static class DateTimeSerializer extends StdSerializer<DateTime> {

    /**
     * Creates a new DateTimeSerializer instance.
     */
    protected DateTimeSerializer() {
      super(DateTime.class);
    }

    /**
     * Serializes a DateTime object to JSON.
     *
     * <p>The DateTime is formatted as an ISO 8601 string in UTC timezone.
     * Null values are serialized as JSON null.
     *
     * @param value the DateTime value to serialize
     * @param gen the JSON generator
     * @param provider the serializer provider
     * @throws IOException if an I/O error occurs during serialization
     */
    @Override
    public void serialize(DateTime value, JsonGenerator gen, SerializerProvider provider)
        throws IOException {
      if (value == null) {
        gen.writeNull();
      } else {
        gen.writeString(ISODateTimeFormat.dateTime().withZoneUTC().print(value));
      }
    }
  }
}
