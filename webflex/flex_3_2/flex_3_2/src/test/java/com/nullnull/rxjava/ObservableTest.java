package com.nullnull.rxjava;


import org.junit.Test;
import rx.Observable;
import rx.Observable.OnSubscribe;
import rx.Subscriber;


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

}
