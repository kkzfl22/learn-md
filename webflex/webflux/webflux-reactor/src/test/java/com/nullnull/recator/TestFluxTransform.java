package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.util.function.Tuple2;

import java.util.function.Function;

/**
 * 组合和转换响应式流
 *
 * @author nullnull
 * @since 2024/12/9
 */
public class TestFluxTransform {

    @Test
    public void transform() {
        Function<Flux<String>, Flux<String>> logUserInfo = stream ->
                stream.index().doOnNext(
                        item -> System.out.println("时间戳:" + item.getT1() + ",元素：" + item.getT2())
                ).map(Tuple2::getT2);

        Flux.range(100, 3)
                .map(i -> "转换-" + i)
                .transform(logUserInfo)
                .subscribe(item -> System.out.println("next:" + item),
                        e -> System.out.println("error:" + e),
                        () -> System.out.println("finish")
                );
    }
}
