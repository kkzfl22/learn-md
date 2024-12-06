package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

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

}
