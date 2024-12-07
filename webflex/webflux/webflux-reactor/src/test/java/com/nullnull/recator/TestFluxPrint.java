package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.scheduler.Schedulers;

import java.time.Duration;

/**
 * 打印
 *
 * @author nullnull
 * @since 2024/12/6
 */
public class TestFluxPrint {


    @Test
    public void doOnEach() {
        Flux.just(1, 2, 3)
                .concatWith(Flux.error(new RuntimeException("手动异常")))
                .doOnEach(item -> System.out.println("打印：" + item))
                .subscribe();
    }

    @Test
    public void doSign() {
        Flux.just(1, 2, 3)
                .doOnNext(item -> System.out.println("doOnNext打印：" + item))
                .doOnComplete(() -> System.out.println("doOnComplete完成信号打印:"))
                .doOnError(e -> System.out.println("doOnError异常信号打印" + e))
                .doOnSubscribe(item -> System.out.println("订阅时打印:"))
                .doOnRequest((t) -> System.out.println("doOnRequest请求时打印:" + t))
                .doOnTerminate(() -> System.out.println("任何终止信号都会收到："))
                .doOnEach(item -> System.out.println("打印：" + item))
                .subscribe();
    }

    @Test
    public void materialize() throws InterruptedException {
        Flux.just(1, 2, 3).delayElements(Duration.ofMillis(200))
                .publishOn(Schedulers.parallel())
                .concatWith(Flux.error(new IllegalArgumentException("手动异常")))
                //将异常流转换为信号
                .materialize()

                .doOnEach(signalSignal -> System.out.println("item:" + signalSignal.isOnComplete()))
                .subscribe(item -> System.out.println("next:" + item),
                        e -> System.out.println("error:" + e),
                        () -> System.out.println("完成"));

        Thread.sleep(3000);
    }

    @Test
    public void materialize2() throws InterruptedException {
        Flux.just(1, 2, 3).delayElements(Duration.ofMillis(200))
                .publishOn(Schedulers.parallel())
                .concatWith(Flux.error(new IllegalArgumentException("手动异常")))
                .doOnEach(signalSignal -> System.out.println("item:" + signalSignal.isOnComplete()))
                .subscribe(item -> System.out.println("next:" + item));

        Thread.sleep(3000);
    }

    @Test
    public void materializeEach() throws InterruptedException {
        Flux.just(1, 2, 3).delayElements(Duration.ofMillis(200))
                .publishOn(Schedulers.parallel())
                .concatWith(Flux.error(new IllegalArgumentException("手动异常")))
                .doOnNext(item -> System.out.println("doOnNext信号:" + item))
                .materialize()
                //日志输出
                .log()
                //非物化处理
                .dematerialize()
                .doOnEach(signalSignal -> System.out.println("item:" + signalSignal.isOnComplete()))
                .subscribe(item -> System.out.println("next:" + item));

        Thread.sleep(3000);
    }


}
