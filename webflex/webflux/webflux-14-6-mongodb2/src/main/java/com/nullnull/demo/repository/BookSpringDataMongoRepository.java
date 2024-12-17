package com.nullnull.demo.repository;

import com.nullnull.demo.entity.Book;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;

/**
 * @author nullnull
 * @since 2024/12/17
 */
@Repository
public interface BookSpringDataMongoRepository extends ReactiveMongoRepository<Book, ObjectId> {

    Flux<Book> findByAuthorsOrderByPublishingYearDesc(String... authors);

    @Query("{'authors.1': { $exists: true }}")
    Flux<Book> booksWithFewAuthors();
}
