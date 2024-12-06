package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;

/**
 * 采样
 *
 * @author nullnull
 * @since 2024/12/6
 */
public class TestFluxSample {


    @Test
    public void sample() throws InterruptedException {
        Flux.range(1, 50)
                .delayElements(Duration.ofMillis(10))
                .sample(Duration.ofMillis(100))
                .subscribe(System.out::println);
        Thread.sleep(5000L);
    }

    @Test
    public void sampleTimeout() throws InterruptedException {
        Random rand = new Random();
        Flux.range(1, 20)
                .delayElements(Duration.ofMillis(100))
                //并发计算超时时间，调节速度快慢
                .sampleTimeout(item -> Mono.delay(Duration.ofMillis(rand.nextInt(100) + 50)), 20)
                .subscribe(System.out::println);

        Thread.sleep(2000);
    }


}
