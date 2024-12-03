package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

import java.time.Duration;

/**
 * 操作符
 *
 * @author nullnull
 * @since 2024/12/3
 */
public class TestFluxOperator {


    @Test
    public void testIndex() {
        Flux.range(1, 10)
                //数据值转换，添加前缀ds->
                .map(item -> "ds->" + item)
                //给每个值生成一个序列，从0开始
                .index()
                //订阅输出
                .subscribe(System.out::println);
    }

    @Test
    public void testTimestamp() throws InterruptedException {
        Flux
                //每50毫秒产生一个元素
                .interval(Duration.ofMillis(50))
                //数值转换
                .map(item -> "ds->" + item)
                //生成时间戳
                .timestamp()
                //订阅处理
                .subscribe(item -> System.out.println("OnNext:" + item),
                        e -> System.out.println("error:" + e),
                        () -> System.out.println("完成信号")
                );

        Thread.sleep(500);
    }

}
