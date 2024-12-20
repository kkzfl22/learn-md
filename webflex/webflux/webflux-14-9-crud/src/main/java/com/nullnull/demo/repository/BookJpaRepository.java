package com.nullnull.demo.repository;

import com.nullnull.demo.entity.Book;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

/**
 * @author nullnull
 * @since 2024/12/20
 */
@Repository
public interface BookJpaRepository extends CrudRepository<Book, Integer> {


    Iterable<Book> findByIdBetween(int lower, int upper);

    @Query("SELECT b FROM Book b WHERE " +
            "LENGTH(b.title)=(SELECT MIN(LENGTH(b2.title)) FROM Book b2)")
    Iterable<Book> findShortestTitle();
}
