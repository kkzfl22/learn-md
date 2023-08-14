package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.Arrays;

/**
 * @author liujun
 * @since 2023/7/15
 */
public class FluxDemo {


    @Test
    public void fluxJust() {
        Flux<String> just = Flux.just("hello", "nullnull");
        just.subscribe(System.out::println);
    }

    @Test
    public void formArrays() {
        Flux.fromArray(new String[]{"hello", "nullnull"}).subscribe(System.out::println);
    }

    @Test
    public void fromIterable() {
        Flux.fromIterable(Arrays.asList(1, 2, 3, 4, 5, 6)).subscribe(System.out::println);
    }

    @Test
    public void range() {
        Flux.range(100, 5).subscribe(System.out::println);
    }

}
