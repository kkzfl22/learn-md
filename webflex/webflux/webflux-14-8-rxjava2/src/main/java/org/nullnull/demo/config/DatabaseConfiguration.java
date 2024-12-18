package org.nullnull.demo.config;

import org.davidmoten.rx.jdbc.Database;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author nullnull
 * @since 2024/12/18
 */
@Configuration
public class DatabaseConfiguration {

  @Bean
  public Database database(
      @Value("${spring.datasource.url}") String url,
      @Value("${spring.datasource.username}") String username,
      @Value("${spring.datasource.password}") String password,
      @Value("${rxjava2jdbc.pool.size}") int poolSize) {

    Database database =
        Database.nonBlocking()
            .url(url)
            .user(username)
            .password(password)
            .maxPoolSize(poolSize)
            .build();

    return database;
  }
}
