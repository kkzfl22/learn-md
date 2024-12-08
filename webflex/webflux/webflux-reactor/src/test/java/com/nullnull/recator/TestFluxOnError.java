package com.nullnull.recator;

import org.junit.Test;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.Random;
import java.util.concurrent.CountDownLatch;

/**
 * 错误处理
 *
 * @author nullnull
 * @since 2024/12/8
 */
public class TestFluxOnError {


    /**
     * 仅处理OnNext事件，没有处理OnError，则抛出异常
     */
    @Test
    public void onError() {
        Flux.from(new Publisher<String>() {
            @Override
            public void subscribe(Subscriber<? super String> s) {
                s.onError(new RuntimeException("手动异常"));
            }
        }).subscribe(System.out::println);
    }

    /**
     * 处理Error信号，则不再抛出
     */
    @Test
    public void onError1() {
        Flux.from(new Publisher<String>() {
            @Override
            public void subscribe(Subscriber<? super String> s) {
                s.onError(new RuntimeException("手动异常"));
            }
        }).subscribe(System.out::println,
                e -> System.out.println("error:" + e),
                () -> System.out.println("完成信号")
        );
    }


    private static Random rand = new Random();

    public static Flux<String> recommendedBooks(String userId) {
        return Flux.defer(() -> {
            if (rand.nextInt(10) < 8) {
                return Flux.<String>error(new RuntimeException("异常信息"))
                        //指定向后推移指定时间，元素发射频率不变
                        .delaySequence(Duration.ofMillis(100));
            } else {
                return Flux.just("JAVA编程思想", "重构")
                        .delayElements(Duration.ofMillis(50));
            }
        }).doOnSubscribe(item -> System.out.println("请求:" + userId));
    }


    @Test
    public void OnErrorMap() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Flux.just("user-001")
                .flatMap(user -> recommendedBooks(user))
                .onErrorMap(throwable -> {
                    if (throwable.getMessage().equals("异常信息")) {
                        return new Exception("转换异常");
                    } else {
                        return new Exception("未知");
                    }
                })
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("完成信号:");
                            latch.countDown();
                        }
                )
        ;
        System.out.println("执行等待开始");
        latch.await();
        System.out.println("执行等待结束");
    }


    @Test
    public void onErrorReturn() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);

        Flux.just("user-001")
                .flatMap(user -> recommendedBooks(user))
                .onErrorReturn("代码大全")
                .subscribe(item -> System.out.println("next:" + item),
                        ex ->{
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("完成信号");
                            latch.countDown();
                        }
                );

        System.out.println("执行等待开始");
        latch.await();
        System.out.println("执行等待结束");
    }


    @Test
    public void onErrorResume() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);


        Flux.just("user-001")
                .flatMap(user -> recommendedBooks(user))
                .onErrorResume(event -> Flux.just("代码整洁之前"))
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("完成信号");
                            latch.countDown();
                        }
                );

        System.out.println("执行等待开始");
        latch.await();
        System.out.println("执行等待结束");
    }


    @Test
    public void retry() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Flux.just("user-001")
                .flatMap(user -> recommendedBooks(user).retry(3))
                .subscribe(item -> System.out.println("next:" + item),
                        ex -> {
                            System.out.println("error:" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("处理完成");
                            latch.countDown();
                        }
                );
        System.out.println("执行等待开始");
        latch.await();
        System.out.println("执行等待结束");
    }


}
