package org.nullnull.demo.entity;

import java.util.UUID;
import lombok.RequiredArgsConstructor;
import org.davidmoten.rx.jdbc.annotations.Column;
import org.davidmoten.rx.jdbc.annotations.Query;

/**
 * @author nullnull
 * @since 2024/12/18
 */
@Query("select id, title, publishing_year from book order by   publishing_year")
public interface Book {

  @Column
  String id();

  @Column
  String title();

  @Column
  Integer publishing_year();

  static Book of(String title, Integer publishingYear) {
    return new Impl(UUID.randomUUID().toString(), title, publishingYear);
  }

  @RequiredArgsConstructor
  class Impl implements Book {
    private final String id;
    private final String title;
    private final Integer publishingYear;

    @Override
    public String id() {
      return id;
    }

    @Override
    public String title() {
      return title;
    }

    @Override
    public Integer publishing_year() {
      return publishingYear;
    }
  }
}
