package com.nullnull.flex.self;


import org.junit.Test;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.HashSet;
import java.util.Set;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liujun
 * @since 2023/6/19
 */
public class MySelfReactiveTest {

    @Test
    public void dataTest() throws InterruptedException {
        Set<Integer> elements = new HashSet<>();
        for (int i = 0; i < 20; i++) {
            elements.add(i);
        }
        final ExecutorService executorService =
                Executors.newFixedThreadPool(5);
        SelfIterablePublisher<Integer> publisher
                = new SelfIterablePublisher<>(elements, executorService);

        //同步订阅者的测试
        MySubscriber subscriberTmp = new MySubscriber();
        publisher.subscribe(subscriberTmp);


        ////异常订阅者的处理
        //final SelfSubscriber<Integer> subscriber = new SelfSubscriber<>
        //        (Executors.newFixedThreadPool(2)) {
        //    @Override
        //    protected boolean whenNext(Integer element) {
        //        System.out.println("接收到的流元素：" + element);
        //        return true;
        //    }
        //};
        //publisher.subscribe(subscriber);

        Thread.sleep(1000000);

    }


    public class MySubscriber implements Subscriber<Integer> {
        private Subscription sub;

        private int randNum = 5;


        @Override
        public void onSubscribe(Subscription s) {
            System.out.println("调用了订阅方法");
            sub = s;
            sub.request(1);
        }

        @Override
        public void onNext(Integer integer) {
            System.out.println("调用了onNext方法" + integer + ",rand:" + randNum);

            if (integer == 0 || (integer + 1) % randNum == 0) {
                System.out.println("再次request:" + randNum);
                sub.request(randNum);
            }
        }

        @Override
        public void onError(Throwable t) {
            System.out.println("错误处理:" + t);
        }

        @Override
        public void onComplete() {
            System.out.println("结束");
        }
    }

}
