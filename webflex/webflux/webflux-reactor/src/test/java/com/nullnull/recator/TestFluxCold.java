package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.ConnectableFlux;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.UUID;

/**
 * 冷数据与热数据
 *
 * @author nullnull
 * @since 2024/12/8
 */
public class TestFluxCold {

    @Test
    public void cold() {
        Flux<String> coldPublisher = Flux.defer(() -> {
            System.out.println("生成新的数据");
            return Flux.just(UUID.randomUUID().toString());
        });
        System.out.println("尚未收到新的数据");
        coldPublisher.subscribe(item -> System.out.println("next1:" + item));
        coldPublisher.subscribe(item -> System.out.println("next2:" + item));
        System.out.println("为两个订阅者生成了两次数据");
    }


    @Test
    public void ConnectableFlux() {
        Flux<Integer> source = Flux.range(0, 3)
                .doOnSubscribe(s -> System.out.println("对冷发布者的新订阅票据" + s));
        //由冷发布者转变为热发布者
        final ConnectableFlux<Integer> conn = source.publish();
        conn.subscribe(item -> System.out.println("next1:" + item));
        conn.subscribe(item -> System.out.println("next2:" + item));
        System.out.println("所有订阅都都准备好建立连接了");
        //激活为热数据开始执行。
        conn.connect();
    }


    @Test
    public void cache() throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 3)
                .doOnSubscribe(s -> System.out.println("冷发布者的新订阅票据"));
        final Flux<Integer> cacheSource = source.cache(Duration.ofMillis(200));
        cacheSource.subscribe(item -> System.out.println("订阅者1-next:" + item));
        cacheSource.subscribe(item -> System.out.println("订阅者2-next:" + item));
        Thread.sleep(300);
        cacheSource.subscribe(item -> System.out.println("订阅者3-next:" + item));
    }

    @Test
    public void share() throws InterruptedException {
        Flux<Integer> source = Flux.range(0, 5)
                .delayElements(Duration.ofMillis(100))
                .doOnSubscribe(item -> System.out.println("冷发布者新的订阅票据" + item));
        Flux<Integer> cacheSource = source.share();
        cacheSource.subscribe(item -> System.out.println("订阅者1- next:" + item));
        Thread.sleep(400);
        cacheSource.subscribe(item -> System.out.println("订阅者2- next:" + item));
        Thread.sleep(1000);
    }


}
