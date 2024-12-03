package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;

/**
 * 订阅方法
 *
 * @author nullnull
 * @since 2024/12/3
 */
public class TestSubscribe {

    @Test
    public void subscribe() {
        Flux.just("hello", "nullnull", "kk")
                .subscribe(
                        data -> System.out.println("onNext:" + data),
                        ex -> System.out.println("异常信息:" + ex),
                        () -> System.out.println("完成信号")
                );
    }

    @Test
    public void subscribeDoOnNext() {
        Flux.range(10, 20)
                //过滤能被5整除的数
                .filter(item -> item % 5 == 0)
                //数据转换，添加前缀v，并转换为字符串
                .map(num -> "v->" + num)
                // 一个操作符，用于在发送新的数据项之前执行一些操作，但它不改变数据流本身
                .doOnNext(System.out::println)
                .subscribe();
    }

    @Test
    public void subscribeOk() {
        Flux.range(1, 20)
                .filter(item -> item % 4 == 0)
                .subscribe(System.out::println);
    }

    @Test
    public void subscribeException() {
        Flux.from(subscriber -> {
            for (int i = 0; i < 5; i++) {
                subscriber.onNext(i);
            }
            subscriber.onError(new IllegalArgumentException("添加错误"));
        }).subscribe(item -> System.out.println("item:" + item),
                ex -> System.out.println("异常情况:" + ex)
        );
    }

    @Test
    public void subscribeFinish() {
        Flux.from(subscriber -> {
            for (int i = 0; i < 5; i++) {
                subscriber.onNext(i);
            }
            subscriber.onComplete();
            ;
        }).subscribe(item -> System.out.println("item:" + item),
                ex -> System.out.println("异常情况:" + ex),
                () -> System.out.println("处理完成")
        );
    }

    @Test
    public void subscribeCancel() {
        Flux.range(1, 20)
                .subscribe(item -> System.out.println("onNext:" + item),
                        ex -> System.out.println("异常情况:" + ex),
                        () -> System.out.println("处理完成"),
                        //一订阅成功就取消订阅
                        subscription -> subscription.cancel()
                );
    }

    @Test
    public void subscribeRequest() {
        Flux.range(1, 20)
                .subscribe(item -> System.out.println("onNext:" + item),
                        ex -> System.out.println("异常情况:" + ex),
                        () -> System.out.println("处理完成"),
                        subscription -> {
                            //订阅响应式流5个元素
                            subscription.request(5);
                            //取消订阅
                            subscription.cancel();
                        }
                );
    }

}
