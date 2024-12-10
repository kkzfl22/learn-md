package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;
import java.time.LocalDateTime;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

/**
 * 并行的调度模型
 *
 * @author nullnull
 * @since 2024/12/10
 */
public class TestWebFluxParallel {

    @Test
    public void parallel() throws InterruptedException {
        Random rand = new Random();
        CountDownLatch downLatch = new CountDownLatch(1);
        Flux.range(1, 32)
                .doOnNext((item) ->
                        System.out.println("parallel:" + Thread.currentThread().getName() + ",item --> " + item)
                )
                //轮询方式将元素交给保修处理器核心来处理，这里只是准备阶段，需要调度runOn真正调度执行。
                .parallel()
                //每个处理器一个执行单元，12c16t，线程名称：runOn:parallel-{1-16}
                .runOn(Schedulers.parallel())
                //执行转换操作
                .map(num -> num + rand.nextInt(10000))
                .doOnNext(item -> System.out.println("map:" + Thread.currentThread().getName()))
                //仅过滤也偶数信息
                .filter(num -> num % 2 == 0)
                .doOnNext((item) -> System.out.println("filter:" + Thread.currentThread().getName()))
                .subscribe(item -> System.out.println(Thread.currentThread().getName() + "-" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            downLatch.countDown();
                        },
                        () -> {
                            System.out.println("finish线程:" + Thread.currentThread().getName());
                            downLatch.countDown();
                        }
                )
        ;


        System.out.println("等待开始:" + LocalDateTime.now());
        downLatch.await(5, TimeUnit.SECONDS);
        System.out.println("等待结束:" + LocalDateTime.now());
    }



}
