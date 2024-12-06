package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.stream.Stream;

/**
 * 将响应式流转换为阻塞结构
 *
 * @author nullnull
 * @since 2024/12/6
 */
public class TestFluxBlock {


    @Test
    public void testToIterable() {
        Iterable<Integer> iterable = Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .toIterable();
        System.out.println("-----------------");
        for (Integer value : iterable) {
            System.out.println("item:" + value);
        }
        System.out.println("finish");
    }

    @Test
    public void testToIterableForEach() {
        Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .toIterable()
                .forEach(item -> System.out.println(item));
    }

    @Test
    public void toStream() {
        Stream<Integer> stream = Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .toStream();
        System.out.println("-----------------");
        stream.forEach((item) -> System.out.println("each:" + item));
        System.out.println("finish");
    }

    @Test
    public void blockFirst() throws InterruptedException {
        Integer blockFirst = Flux.just(1, 2, 3)
                .delayElements(Duration.ofMillis(500))
                .doOnNext(System.out::println)
                //此会阻塞主线程，直到收到第一个元素
                .blockFirst();
        System.out.println("-----------");
        System.out.println("获取值:" + blockFirst);
        Thread.sleep(5000L);
    }

    @Test
    public void blockLast() {
        Integer i = Flux.just(1, 2, 3)
                .delayElements(Duration.ofSeconds(1))
                .doOnEach(System.out::println)
                //该方法会阻塞，直到等到流中的最后一个元素或者完成
                .blockLast();
        System.out.println("----------");
        System.out.println("结果" + i);
        System.out.println("finish");
    }

    @Test
    public void blockLastSub() throws InterruptedException {
        Flux<Integer> integerFlux = Flux.just(1, 2, 3).delayElements(Duration.ofSeconds(1));
        integerFlux.subscribe(integer -> System.out.println("第一个订阅：" + integer));
        integerFlux.subscribe(integer -> System.out.println("第二个订阅：" + integer));
        integerFlux.subscribe(integer -> System.out.println("第三个订阅：" + integer));

        System.out.println("订阅结束...");

        Integer i = integerFlux.blockLast();
        System.out.println("阻塞等待最后一个元素：");
        System.out.println("value:" + i);
        Thread.sleep(5000);
    }
}
