package com.nullnull.flex.self;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;
import org.reactivestreams.tck.SubscriberWhiteboxVerification;
import org.reactivestreams.tck.TestEnvironment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author nullnull
 * @since 2024/11/27
 */
public class TestWhiteBoxTest extends SubscriberWhiteboxVerification<Integer> {

    protected TestWhiteBoxTest() {
        super(new TestEnvironment());
    }


    @Override
    public Subscriber<Integer> createSubscriber(WhiteboxSubscriberProbe<Integer> whiteboxSubscriberProbe) {

        final ExecutorService executorService =
                Executors.newFixedThreadPool(5);

        AsyncSubscriber subscriber = new AsyncSubscriber(executorService) {
            @Override
            protected boolean whenNext(Object element) {
                System.out.println("接收到的元素：" + element);
                //返回true表示请求下一个元素，false表示不再请求
                return true;
            }

            @Override
            public void onSubscribe(Subscription subscription) {
                super.onSubscribe(subscription);
                whiteboxSubscriberProbe.registerOnSubscribe(new SubscriberPuppet() {
                    @Override
                    public void triggerRequest(long elements) {
                        subscription.request(elements);
                    }

                    @Override
                    public void signalCancel() {
                        subscription.cancel();
                    }
                });
            }

            @Override
            public void onNext(Object object) {
                super.onNext(object);
                whiteboxSubscriberProbe.registerOnNext((Integer) object);
            }

            @Override
            public void onError(Throwable t) {
                super.onError(t);
                whiteboxSubscriberProbe.registerOnError(t);
            }

            @Override
            public void onComplete() {
                super.onComplete();
                whiteboxSubscriberProbe.registerOnComplete();
            }
        };


        return subscriber;
    }

    @Override
    public Integer createElement(int i) {
        return i;
    }
}
