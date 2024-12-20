package com.nullnull.demo.adapter;

import lombok.RequiredArgsConstructor;
import org.reactivestreams.Publisher;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Scheduler;

/**
 * @author nullnull
 * @since 2024/12/20
 */
@RequiredArgsConstructor
public abstract class ReactiveCrudRepositoryAdapter<T, ID, I extends CrudRepository<T, ID>>
        implements ReactiveCrudRepository<T, ID> {

    protected final I delegate;
    protected final Scheduler scheduler;


    @Override
    public <S extends T> Mono<S> save(S entity) {
        return Mono.fromCallable(() -> delegate.save(entity))
                .subscribeOn(scheduler);
    }


    @Override
    public <S extends T> Flux<S> saveAll(Iterable<S> iterable) {
        return Mono.fromCallable(() -> delegate.saveAll(iterable))
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(scheduler);
    }

    @Override
    public <S extends T> Flux<S> saveAll(Publisher<S> publisher) {
        return Flux.from(publisher)
                .flatMap(entity -> Mono.fromCallable(() -> delegate.save(entity)))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<T> findById(ID id) {
        return Mono.fromCallable(() -> delegate.findById(id))
                .flatMap(result -> result
                        .map(Mono::just)
                        .orElseGet(Mono::empty)
                ).subscribeOn(scheduler);
    }

    @Override
    public Mono<T> findById(Publisher<ID> publisher) {
        return Mono.from(publisher)
                .flatMap(actualId -> delegate.findById(actualId)
                        .map(Mono::just)
                        .orElseGet(Mono::empty))
                .subscribeOn(scheduler)
                ;
    }

    @Override
    public Mono<Boolean> existsById(ID id) {
        return Mono.fromCallable(() -> delegate.existsById(id))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Boolean> existsById(Publisher<ID> publisher) {
        return Mono.from(publisher)
                .flatMap(actualId ->
                        Mono.fromCallable(() ->
                                delegate.existsById(actualId))
                )
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<T> findAll() {
        return Mono
                .fromCallable(delegate::findAll)
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<T> findAllById(Iterable<ID> iterable) {
        return Mono.fromCallable(() -> delegate.findAllById(iterable))
                .flatMapMany(Flux::fromIterable)
                .subscribeOn(scheduler);
    }

    @Override
    public Flux<T> findAllById(Publisher<ID> publisher) {
        return Flux.from(publisher)
                .buffer()
                .flatMap(ids ->
                        Flux.fromIterable(delegate.findAllById(ids))
                )
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Long> count() {
        return Mono.fromCallable(delegate::count)
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteById(ID id) {
        return Mono.<Void>fromRunnable(() -> delegate.deleteById(id))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteById(Publisher<ID> publisher) {
        return Mono.from(publisher)
                .flatMap(actualId ->
                        Mono.<Void>fromRunnable(() -> delegate.deleteById(actualId))
                )
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> delete(T t) {
        return Mono.<Void>fromRunnable(() -> delegate.delete(t))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteAll(Iterable<? extends T> iterable) {
        return Mono.<Void>fromRunnable(() -> delegate.deleteAll(iterable))
                .subscribeOn(scheduler);
    }

    @Override
    public Mono<Void> deleteAll(Publisher<? extends T> publisher) {
        return Flux.from(publisher)
                .flatMap(entity ->
                        Mono.fromRunnable(() -> delegate.delete(entity)))
                .subscribeOn(scheduler)
                .then();
    }

    @Override
    public Mono<Void> deleteAll() {
        return Mono.<Void>fromRunnable(delegate::deleteAll)
                .subscribeOn(scheduler);
    }
}
