package com.nullnull.rxjava;


import org.junit.Test;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;
import rx.Subscription;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;


/**
 * @author liujun
 * @since 2023/7/13
 */
public class ObservableTest {

    @Test
    public void rxjava1() {

        //创建发布者
        Observable<String> observable = Observable.create(
                new Observable.OnSubscribe<String>() {
                    @Override
                    public void call(Subscriber<? super String> subscriber) {
                        for (int i = 0; i < 10; i++) {
                            subscriber.onNext("hello null null " + i);
                        }
                        subscriber.onCompleted();
                    }
                });

        //创建订阅者
        observable.subscribe(new Subscriber<String>() {
            @Override

            public void onCompleted() {
                System.out.println("on completed");
            }

            @Override

            public void onError(Throwable throwable) {
                System.out.println("on error :" + throwable.getMessage());
            }

            @Override

            public void onNext(String s) {
                System.out.println("on next: " + s);
            }
        });
    }

    @Test
    public void rxjava1Lambda() {
        //创建发布者，使用Lambda表达式
        Observable.create(
                        subscriber -> {
                            for (int i = 0; i < 10; i++) {
                                subscriber.onNext("hello null null " + i);
                            }
                            subscriber.onCompleted();
                        }
                )
                //指定了调用者
                .subscribe(
                        System.out::println,
                        System.err::println,
                        () -> System.out.println("结束")
                );

    }

    @Test
    public void testJust() {
        Observable<String> just = Observable.just("1", "2", "3", "4", "5");
        just.subscribe(
                item -> System.out.println(item),
                ex -> System.err.println(ex),
                () -> System.out.println("完成")
        );
    }


    @Test
    public void testFrom() {
        Observable<Integer> from = Observable.from(new Integer[]{1, 2, 3, 4, 5});
        from.subscribe(
                itm -> System.out.println("下一个元素是:" + itm),
                ex -> System.err.println("异常信息是:" + ex),
                () -> System.out.println("结束")
        );
    }


    @Test
    public void testFromCallable() {
        Observable<String> fromCallable = Observable.fromCallable(() -> "hello null null");

        fromCallable.subscribe(
                item -> System.out.println("下一个元素是：" + item),
                ex -> System.out.println("错误信息是：" + ex),
                () -> System.out.println("结束")
        );
    }

    @Test
    public void testFeature() {
        Future<String> submit = Executors.newCachedThreadPool().submit(() -> "hello word");

        Observable<String> from = Observable.from(submit);

        from.subscribe(
                item -> System.out.println("下一个元素是：" + item),
                ex -> System.out.println("错误信息是：" + ex),
                () -> System.out.println("结束")
        );
    }

    @Test
    public void testConcat() {
        Observable.concat(
                Observable.just("hello "),
                Observable.from(new String[]{"null null"}),
                Observable.just("!")
        ).forEach(
                item -> System.out.println("下一个元素：" + item),
                ex -> System.out.println(ex),
                () -> System.out.println("结束")
        );
    }

    @Test
    public void testInterval() throws InterruptedException {
        Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(
                        item -> System.out.println("下一个元素：" + item),
                        ex -> System.err.println("异常:" + ex),
                        () -> System.out.println("完成")
                );
        Thread.sleep(5000);
    }

    @Test
    public void testUnsubscribe() throws InterruptedException {
        CountDownLatch latch = new CountDownLatch(1);
        Subscription subscribe = Observable.interval(1, TimeUnit.SECONDS)
                .subscribe(
                        item -> System.out.println("下一个元素:" + item),
                        ex -> System.err.println("异常:" + ex),
                        () -> System.out.println("完成:")
                );

        new Thread(() -> {
            try {
                Thread.sleep(3000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            //如果订阅票据还在订阅状态，则取消订阅
            if (!subscribe.isUnsubscribed()) {
                subscribe.unsubscribe();
            }
            latch.countDown();
        }).start();

        System.out.println("主线程完成");
        latch.await();
        System.out.println("等待结束");
    }

    /**
     * Map函数用于对象的转换操作
     */
    @Test
    public void map1() {
        Observable<Integer> just = Observable.just(1, 2, 3, 4, 5, 6);
        just.map(item -> {
            String[] value = new String[item];
            Arrays.fill(value, "n");
            return value;
        }).forEach(item -> System.out.println(Arrays.toString(item)));
    }

    /**
     * Map函数用于对象的转换操作
     */
    @Test
    public void map2() {
        Observable<Integer> just = Observable.just(1, 2, 3, 4, 5, 6);
        just.map(item -> {
            return item * 100;
        }).forEach(item -> System.out.println(item));
    }

    @Test
    public void filter() {
        Observable<Integer> just = Observable.just(1, 2, 3, 4, 5, 6);
        just.filter(item -> {
            return item % 2 == 0;
        }).forEach(item -> System.out.println(item));
    }

    @Test
    public void count() {
        List<Integer> dataList = new ArrayList<>(1000);

        for (int i = 0; i < 1000; i++) {
            dataList.add(i);
        }

        Observable.from(dataList)
                //过滤出偶数
                .filter(item -> item % 2 == 0)
                //计数
                .count()
                //输出
                .subscribe(
                        item -> System.out.println("下一个元素:" + item),
                        ex -> System.err.println("异常:" + ex),
                        () -> System.out.println("完成:")
                );
    }


    @Test
    public void zip() {
        Observable.zip(Observable.just(1,2,3,4),
                Observable.just("a","b","C","D"),
                (a ,b) ->  a + b)
                .forEach(System.out::println);

    }

}
