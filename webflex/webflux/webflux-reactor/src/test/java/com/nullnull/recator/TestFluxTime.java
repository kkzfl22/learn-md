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
 * 时间处理
 *
 * @author nullnull
 * @since 2024/12/8
 */
public class TestFluxTime {


    @Test
    public void interval() throws InterruptedException {
        //指定每个元素之间的时间间隔
        Flux.interval(Duration.ofMillis(200))
                .subscribe(item -> System.out.println(Thread.currentThread().getName() + "---->" + item));
        System.out.println("当前的线程名称:" + Thread.currentThread().getName());
        Thread.sleep(2000);
        System.out.println("结束");
    }

    @Test
    public void interval2() throws InterruptedException {
        //参数1：指定第一个元素发送的时间与订阅时间的时间差。
        //参数2： 指定生成的序列元素之间的时间间隔。
        Flux.interval(Duration.ofSeconds(2), Duration.ofMillis(200))
                .subscribe(item -> System.out.println("onext:" + item));
        Thread.sleep(3000);
        System.out.println("结束");
    }

    @Test
    public void interval3() throws InterruptedException {
        Flux.interval(Duration.ofMillis(200), Schedulers.parallel())
                .subscribe(item -> System.out.println(Thread.currentThread().getName() + "---->" + item));
        Thread.sleep(3000);
        System.out.println("结束");
    }


    @Test
    public void interval4() throws InterruptedException {
        //新创建的线程也非守护线程
        Flux.interval(Duration.ofMillis(100), Schedulers.newSingle("sch-"))
                .subscribe(item -> System.out.println(Thread.currentThread().getName() + "---->" + item));
        Thread.sleep(5000);
        System.out.println("结束");
    }

    @Test
    public void delayElements() throws InterruptedException {
        Flux.range(1, 10)
                //对每个元素之间的时间间隔进行指定时间
                .delayElements(Duration.ofSeconds(1))
                .subscribe(item -> System.out.println(Thread.currentThread().getName() + "---->" + item));
        Thread.sleep(5000);
        System.out.println("结束");
    }

    @Test
    public void delaySequence() throws InterruptedException {
        Flux.range(1, 10)
                //指定数据延迟发布的时间，即数据整体延迟1秒后全部发出
                .delaySequence(Duration.ofSeconds(3))
                .subscribe(item -> System.out.println(Thread.currentThread().getName() + "---->" + item + "<>" + LocalDateTime.now()));
        System.out.println("开始:" + LocalDateTime.now());
        Thread.sleep(5000);
        System.out.println("结束");
    }


    @Test
    public void timeOut() throws InterruptedException {
        Random rand = new Random();
        CountDownLatch latch = new CountDownLatch(1);
        Flux.interval(Duration.ofMillis(300))
                //时间超过指定毫秒后直接报异常
                .timeout(Duration.ofMillis(rand.nextInt(20) + 290))
                .subscribe(item -> System.out.println(item),
                        ex ->
                        {
                            System.out.println("ex:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("finish");
                            latch.countDown();
                        }
                );
        System.out.println("开始:");
        latch.await(3, TimeUnit.SECONDS);
        System.out.println("结束");
    }


    @Test
    public void timestamp() throws InterruptedException {

        Flux.interval(Duration.ofMillis(300))
                .timestamp()
                .subscribe(item -> {
                            Long timestamp = item.getT1();
                            long element = item.getT2();
                            System.out.println("元素：" + element + "的时间戳:" + timestamp);
                        }
                );
        System.out.println("开始:");
        Thread.sleep(3000);
        System.out.println("结束");
    }

    @Test
    public void elapsed() throws InterruptedException {
        Flux.interval(Duration.ofMillis(300))
                .elapsed()
                .subscribe(item -> {
                            Long interval = item.getT1();
                            long element = item.getT2();
                            System.out.println("元素：" + element + "与上一个元素的时间间隔:" + interval + "ms");
                        }
                );
        System.out.println("开始:");
        Thread.sleep(3000);
        System.out.println("结束");
    }

}
