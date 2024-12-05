package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.util.ArrayList;

/**
 * 流的批处理
 *
 * @author nullnull
 * @since 2024/12/5
 */
public class TestFluxBatch {

    @Test
    public void buffer() {
        Flux.range(100, 13)
                .buffer(3).
                subscribe(System.out::println);
    }

    @Test
    public void windowUntil() {
        Flux.range(101, 20)
                .windowUntil(this::isPrime)
                .subscribe(window -> window.collectList()
                        .subscribe(item -> System.out.println("windows:" + item)));
    }


    private boolean isPrime(Integer value) {
        if (value < 2) {
            return false;
        }
        if (value == 2 || value == 3) {
            return true;
        }
        if (value % 2 == 0) {
            return false;
        }

        double sqrt = Math.sqrt(value);
        for (int i = 3; i < sqrt; i++) {
            if (value % i == 0) {
                return false;
            }
        }

        return true;
    }

    @Test
    public void groupBy() {
        Flux.range(1, 11)
                .groupBy(item -> item % 2 == 0 ? "偶数" : "奇数")
                .subscribe(groupFlux -> {
                    groupFlux.scan(new ArrayList<>(),
                                    (list, element) -> {
                                        list.add(element);
                                        if (list.size() > 3) {
                                            list.remove(0);
                                        }
                                        return list;
                                    })
                            .filter(list -> !list.isEmpty())
                            .subscribe(item -> System.out.println("key:" + groupFlux.key() + "-->" + item));
                });
    }

}
