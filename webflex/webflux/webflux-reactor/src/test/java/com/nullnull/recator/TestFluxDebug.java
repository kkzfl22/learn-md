package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Hooks;

/**
 * @author nullnull
 * @since 2024/12/9
 */
public class TestFluxDebug {

    @Test
    public void onDebug()
    {
        Hooks.onOperatorDebug();

        Flux.range(1,10)
                .map(item -> "map-"+item)
                .concatWith(Flux.error(new RuntimeException("手动异常")))
                .subscribe(System.out::println);
    }


    @Test
    public void onLog() {
        Flux.range(1,5)
                .map(item -> "map-"+item)
                .concatWith(Flux.error(new RuntimeException("手动异常")))
                .log()
                .subscribe(System.out::println);
    }
}
