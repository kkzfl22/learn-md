package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.lang.reflect.Array;
import java.time.Duration;
import java.util.Arrays;
import java.util.Random;

/**
 * flatMap、concatMap和FlatMapSequential操作符
 *
 * @author nullnull
 * @since 2024/12/5
 */
public class TestFluxMap {


    @Test
    public void floatMap() throws InterruptedException {
        Random ran = new Random();
        Flux.just(Arrays.asList(1, 2, 3), Arrays.asList("a", "b", "c"),
                        Arrays.asList(7, 8, 9))
                .doOnNext(System.out::println)
                .flatMap(item -> Flux.fromIterable(item)
                        .delayElements(Duration.ofMillis(ran.nextInt(100) + 100))
                        .doOnSubscribe(subscription -> System.out.println("已经订阅"))
                ).subscribe(System.out::println);

        Thread.sleep(2000);
    }

    @Test
    public void concatMap() throws InterruptedException {
        Random ran = new Random();
        Flux.just(Arrays.asList(1, 2, 3), Arrays.asList("a", "b", "c"),
                        Arrays.asList(7, 8, 9))
                .doOnNext(System.out::println)
                .concatMap(item -> Flux.fromIterable(item)
                        .delayElements(Duration.ofMillis(ran.nextInt(100) + 100))
                        .doOnSubscribe(subscription -> System.out.println("已经订阅"))
                ).subscribe(System.out::println);

        Thread.sleep(2000);
    }

    @Test
    public void flatMapSequential() throws InterruptedException {
        Random ran = new Random();
        Flux.just(Arrays.asList(1, 2, 3), Arrays.asList("a", "b", "c"),
                        Arrays.asList(7, 8, 9))
                .flatMapSequential(item -> Flux.fromIterable(item)
                        .delayElements(Duration.ofMillis(ran.nextInt(100) + 100))
                        .doOnSubscribe(subscription -> System.out.println("订阅"))
                ).subscribe(System.out::println);
        Thread.sleep(2000);
    }

}
