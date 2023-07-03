package com.nullnull.flex.self;


import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liujun
 * @since 2023/6/19
 */
public class ReactiveTest {

    @Test
    public void dataTest() throws InterruptedException {
        Set<Integer> elements = new HashSet<>();
        for (int i = 0; i < 10; i++) {
            elements.add(i);
        }
        final ExecutorService executorService =
                Executors.newFixedThreadPool(5);
        SelfIterablePublisher<Integer> publisher
                = new SelfIterablePublisher<>(elements, executorService);
        publisher.subscribe(new Subscriber<Integer>() {
            @Override
            public void onSubscribe(Subscription s) {
                System.out.println("调用了订阅方法");
                s.request(20);
            }

            @Override
            public void onNext(Integer integer) {
                System.out.println("调用了下一个方法" + integer);
            }

            @Override
            public void onError(Throwable t) {
                System.out.println("错误处理:" + t);
            }

            @Override
            public void onComplete() {
                System.out.println("结束");
            }
        });





        final SelfSubscriber<Integer> subscriber = new SelfSubscriber<>
                (Executors.newFixedThreadPool(2)) {
            @Override
            protected boolean whenNext(Integer element) {
                System.out.println("接收到的流元素：" + element);
                return true;
            }
        };
        publisher.subscribe(subscriber);

        Thread.sleep(1000);

    }
}
