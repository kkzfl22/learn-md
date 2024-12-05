package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 组合响应式流中的元素
 *
 * @author nullnull
 * @since 2024/12/5
 */
public class TestFluxMerge {


    @Test
    public void concat() throws InterruptedException {
        Flux.concat(
                        Flux.range(10, 5).delayElements(Duration.ofMillis(100))
                                .doOnSubscribe((t) -> System.out.println("当前被订阅1:")),
                        Flux.range(100, 5).delayElements(Duration.ofMillis(110))
                                .doOnSubscribe((t) -> System.out.println("当前被订阅2"))
                )
                .subscribe(System.out::println);

        Thread.sleep(2000);
    }

    @Test
    public void merge() throws InterruptedException {
        Flux.merge(
                        Flux.range(10, 5).delayElements(Duration.ofMillis(100))
                                .doOnSubscribe((t) -> System.out.println("当前被订阅1:")),
                        Flux.range(100, 5).delayElements(Duration.ofMillis(110))
                                .doOnSubscribe((t) -> System.out.println("当前被订阅2"))
                )
                .subscribe((t) -> System.out.println("onNext:" + t),
                        e -> System.out.println("exception" + e),
                        () -> System.out.println("finish")
                );
        Thread.sleep(2000);
    }

    @Test
    public void zip() throws InterruptedException {
        Flux.zip(
                Flux.range(0, 6)
                        .delayElements(Duration.ofMillis(100))
                        .doOnSubscribe((t) -> System.out.println("当前被订阅1")),
                Flux.range(100, 5)
                        .delayElements(Duration.ofMillis(500))
                        .doOnSubscribe((t) -> System.out.println("当前被订阅2"))
        ).subscribe((t) -> System.out.println("二元组：t1：" + t.getT1() + ",t2:" + t.getT2()),
                e -> System.out.println("error" + e),
                () -> System.out.println("finish")
        );
        Thread.sleep(5000);
    }

    @Test
    public void combineLatest() throws InterruptedException {
        Flux.combineLatest(
                Flux.range(0, 6)
                        .delayElements(Duration.ofMillis(100))
                        .doOnSubscribe((t) -> System.out.println("当前被订阅1")),
                Flux.range(100, 5)
                        .delayElements(Duration.ofMillis(500))
                        .doOnSubscribe((t) -> System.out.println("当前被订阅2")
                        ),
                (var1, var2) -> "数据1:" + var1 + ",数据2:" + var2
        ).subscribe((t) -> System.out.println("二元组" + t),
                e -> System.out.println("error" + e),
                () -> System.out.println("finish")
        );
        Thread.sleep(5000);
    }

}
