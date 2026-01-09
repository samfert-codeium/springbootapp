package io.spring;

import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Configuration class for MyBatis integration with Spring.
 *
 * <p>This class enables transaction management for the application, allowing
 * MyBatis operations to participate in Spring-managed transactions. The
 * {@code @EnableTransactionManagement} annotation enables Spring's annotation-driven
 * transaction management capability.
 *
 * <p>MyBatis mapper interfaces and XML mappings are automatically discovered
 * through Spring Boot's auto-configuration mechanism.
 *
 * @see org.springframework.transaction.annotation.Transactional
 */
@Configuration
@EnableTransactionManagement
public class MyBatisConfig {}
