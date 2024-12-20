package com.nullnull.demo.repository;

import com.nullnull.demo.adapter.ReactiveCrudRepositoryAdapter;
import com.nullnull.demo.entity.Book;
import org.reactivestreams.Publisher;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

/**
 * @author nullnull
 * @since 2024/12/20
 */
@Component
public class RxBookRepository extends ReactiveCrudRepositoryAdapter<Book, Integer, BookJpaRepository> {

    public RxBookRepository(BookJpaRepository delegate, Scheduler scheduler) {
        super(delegate, scheduler);
    }


    public Flux<Book> findByIdBetween(
            Publisher<Integer> lowerPublisher,
            Publisher<Integer> upperPublisher
    ) {
        return Mono.zip(Mono.from(lowerPublisher),
                        Mono.from(upperPublisher))
                .flatMapMany(item ->
                        Flux.fromIterable(delegate.findByIdBetween(item.getT1(), item.getT2()))
                                .subscribeOn(scheduler)
                ).subscribeOn(scheduler);
    }


    public Flux<Book> findShortesTitle() {
        return Mono.fromCallable(delegate::findShortestTitle)
                .subscribeOn(scheduler)
                .flatMapMany(Flux::fromIterable);
    }
}
