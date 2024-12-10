package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;
import reactor.core.scheduler.Schedulers;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;

/**
 * @author nullnull
 * @since 2024/12/10
 */
public class TestFluxContext {

    @Test
    public void threadLocal() throws InterruptedException {
        Random random = new Random();

        ThreadLocal<Map<Object, Object>> threadLocal = new ThreadLocal<>();
        threadLocal.set(new HashMap<>());
        Flux.range(0, 10)
                .doOnNext(item -> threadLocal.get().put(item, random.nextGaussian()))
                //多线程调度执行
                .publishOn(Schedulers.parallel())
                .map(key -> threadLocal.get().get(key))
                .blockLast();

        Thread.sleep(1000);
    }

    @Test
    public void context() {
        Random random = new Random();

        String key = "keys";

        Object lastValue = Flux.range(0, 10)
                .flatMap(k ->
                        Mono.subscriberContext()
                                .doOnNext(context -> {
                                    Map<Object, Object> map = context.get(key);

                                    map.put(k, random.nextGaussian());
                                }).thenReturn(k)
                )
                //并行化执行
                .publishOn(Schedulers.parallel())
                .flatMap(k ->
                        Mono.subscriberContext()
                                .map(context -> {
                                    Map<Object, Object> map = context.get(key);
                                    return map.get(k);
                                })
                )
                .subscriberContext(context -> context.put(key, new HashMap<>()))
                .blockLast();

        System.out.println("结果：" + lastValue);
    }
}
