package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Mono;

import java.util.Optional;

/**
 * @author liujun
 * @since 2023/7/18
 */
public class MonoDemo {

    @Test
    public void just() {
        Mono.just("hello").subscribe(System.out::println);
    }

    @Test
    public void justOrEmpty() {
        Mono.justOrEmpty(null).subscribe(System.out::println);
    }

    @Test
    public void justOrEmptyOption() {
        Mono.justOrEmpty(Optional.empty()).subscribe(System.out::println);
    }

}
