package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.concurrent.CountDownLatch;

/**
 * 背压处理技术
 *
 * @author nullnull
 * @since 2024/12/8
 */
public class TestFluxBackPressure {


    @Test
    public void OnBackpressureBuffer() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(1, 10)
                .delayElements(Duration.ofMillis(1))
                .onBackpressureBuffer(10)
                .delayElements(Duration.ofMillis(100))
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("完成：");
                            latch.countDown();
                        }
                );

        System.out.println("等待开始");
        latch.await();
        System.out.println("等待结束");
    }


    @Test
    public void onBackpressureDrop() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(1, 100)
                .delayElements(Duration.ofMillis(1))
                .onBackpressureDrop()
                .delayElements(Duration.ofMillis(100))
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("完成：");
                            latch.countDown();
                        }
                );

        System.out.println("等待开始");
        latch.await();
        System.out.println("等待结束");
    }


    @Test
    public void onBackpressureLast() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(1, 500)
                .delayElements(Duration.ofMillis(1))
                .onBackpressureLatest()
                .delayElements(Duration.ofMillis(100))
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("完成：");
                            latch.countDown();
                        }
                );

        System.out.println("等待开始");
        latch.await();
        System.out.println("等待结束");
    }

    @Test
    public void onBackpressureError() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(1, 500)
                .delayElements(Duration.ofMillis(1))
                .onBackpressureError()
                .delayElements(Duration.ofMillis(100))
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("完成：");
                            latch.countDown();
                        }
                );

        System.out.println("等待开始");
        latch.await();
        System.out.println("等待结束");
    }


    @Test
    public void limit() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.range(1, 150)
                .delayElements(Duration.ofMillis(1))
                .limitRate(2)
                .delayElements(Duration.ofMillis(100))
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("完成：");
                            latch.countDown();
                        }
                );

        System.out.println("等待开始");
        latch.await();
        System.out.println("等待结束");
    }

}
