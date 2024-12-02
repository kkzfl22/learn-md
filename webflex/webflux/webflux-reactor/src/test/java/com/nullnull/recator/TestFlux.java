package com.nullnull.recator;


import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * Flux的使用
 *
 * @author nullnull
 * @since 2024/12/2
 */
public class TestFlux {

    /**
     * 使用元素进行构建
     */
    @Test
    public void just() {
        Flux<String> just = Flux.just("hello", "nullnull");
        just.subscribe(System.out::println);
    }

    /**
     * 使用数组构建
     */
    @Test
    public void fromArray() {
        Flux<String> arrayFlux = Flux.fromArray(new String[]{"hello", "nullnull", "dd"});
        arrayFlux.subscribe(System.out::println);
    }


    /**
     * 使用集合构建
     */
    @Test
    public void fromIterable() {
        Flux<Integer> interFlux = Flux.fromIterable(Arrays.asList(1, 2, 3, 4, 5));
        interFlux.subscribe(System.out::println);
    }

    /**
     * 使用range生成，100为起点，生成5个数字
     */
    @Test
    public void range() {
        Flux<Integer> range = Flux.range(100, 5);
        range.subscribe(System.out::println);
    }

    @Test
    public void publisher() {
        Flux.from((subscriber) -> {
                    for (int i = 0; i < 10; i++) {
                        subscriber.onNext("hello" + i);
                    }
                    subscriber.onComplete();
                })
                .subscribe(
                        System.out::println,
                        System.out::println,
                        () -> System.out.println("处理结果")
                );
    }

    public void nullStream() {
        //empty工厂方法，它们分别生成Flux或者Mono的空实例。
        Flux<String> empty = Flux.empty();
        //never方法创建一个永远不会发出完成、数据、或者错误等信号的流
        Flux<String> never = Flux.never();
        //error(Throwable)工厂方法创建一个序列，该序列在订阅时始终通过每个订阅者的onError(....)方法传播错误。
        // 由于错误是在Flux或者Mono声明期间被创建，因此每个订阅者都会收到相同的Throwable实例。
        Mono<String> error = Mono.error(new RuntimeException("错误对象"));
    }

    public boolean isValidSeed(String seed) {
        System.out.println("调用了isValidSeed方法");
        return true;
    }

    public String getData(String seed) {
        try {
            System.out.println("方法执行：5s");
            Thread.sleep(5000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return "echo:" + seed;
    }

    public Mono<String> requestData(String seed) {
        if (isValidSeed(seed)) {
            return Mono.fromCallable(() -> getData(seed));
        } else {
            return Mono.error(new RuntimeException("Invalid seed value"));
        }
    }

    public Mono<String> requestDeferData(String seed) {
        Mono<String> data;
        if (isValidSeed(seed)) {
            data = Mono.fromCallable(() -> getData(seed));
        } else {
            data = Mono.error(new RuntimeException("Invalid seed value"));
        }
        return Mono.defer(() -> data);
    }

    /**
     * 仅调用requestData方法
     */
    @Test
    public void request() {
        requestData("nullnull");
    }

    /**
     * 调用requestDeferData方法不订阅
     */
    @Test
    public void requestDeferData() {
        requestDeferData("nullnull");
    }

    /**
     * 调用requestDeferData并订阅
     */
    @Test
    public void requestDeferDataSubscribe() {
        requestDeferData("nullnull").subscribe();
    }
}
