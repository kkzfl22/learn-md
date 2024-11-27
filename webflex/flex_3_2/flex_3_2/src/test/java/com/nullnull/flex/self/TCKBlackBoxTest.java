package com.nullnull.flex.self;

import org.reactivestreams.Subscriber;
import org.reactivestreams.tck.SubscriberBlackboxVerification;
import org.reactivestreams.tck.TestEnvironment;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @author liujun
 * @since 2024/11/27
 */
public class TCKBlackBoxTest extends SubscriberBlackboxVerification<Integer> {


    protected TCKBlackBoxTest() {
        super(new TestEnvironment());
    }

    @Override
    public Subscriber<Integer> createSubscriber() {
        final ExecutorService executorService =
                Executors.newFixedThreadPool(5);
        AsyncSubscriber subscriber = new AsyncSubscriber(executorService) {
            @Override
            protected boolean whenNext(Object element) {
                System.out.println("接收到的元素:" + element);
                //该返回为true，表示继续接收下一个元素，false表示不再请求了
                return true;
            }
        };
        return subscriber;
    }

    @Override
    public Integer createElement(int i) {
        return i;
    }

    //@Override
    //public void triggerRequest(Subscriber<? super Integer> subscriber) {
    //    //该方法直接向订阅者发送信号，默认该方法什么都不做
    //    AsyncSubscriber<Integer> subscriber1 = (AsyncSubscriber<Integer>) subscriber;
    //    subscriber1.onNext(100000);
    //}
}
