package com.nullnull.flex.self;

import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * 自定义订阅者信息
 * <p>
 * 基于Executor的异步运行的订阅者实现，一次请求一个元素，然后对每个元素调用用户定义的方
 * 法进行处理。
 * 注意：该类中使用了很多try-catch用于说明什么时候可以抛异常，什么时候不可以抛异常
 *
 * @author liujun
 * @since 2023/6/30
 */
public class SelfSubscriber<T> implements Subscriber<T>, Runnable {

    /**
     * Signal表示发布者和订阅者之间的异步协议
     */
    private static interface Signal {
    }

    /**
     * 表示数据流发送完成，完成信号
     */
    private enum onComplete implements Signal {
        Instance;
    }

    /**
     * 错误信号
     */
    private static class onError implements Signal {
        private final Throwable t;

        public onError(Throwable t) {
            this.t = t;
        }
    }

    /**
     * 表示下一个数据项信号
     *
     * @param <T>
     */
    private static class onNext<T> implements Signal {
        private T next;

        public onNext(T next) {
            this.next = next;
        }
    }

    /**
     * 表示订阅者的订阅成功信号
     */
    private static class onSubscribe implements Signal {
        private Subscription subscription;

        public onSubscribe(Subscription subscription) {
            this.subscription = subscription;
        }
    }


    /**
     * 订阅单据,根据规范3.1，该引用是私有的
     */
    private Subscription subscription;

    /**
     * 用于表示当前的订阅者是否处理完成
     */
    private boolean done;

    /**
     * 根据规范的2.2条款，使用该线程池异步处理各个信号
     */
    private final Executor executor;


    /**
     * 存储信号的队列
     */
    private final ConcurrentLinkedQueue<Signal> inboundSignals = new ConcurrentLinkedQueue<>();


    /**
     * 根据规范2.7和2.11，使用原子变量确保不会有多个订阅者线程并发执行。
     */
    private final AtomicBoolean on = new AtomicBoolean(false);


    /**
     * 仅有这一个构造器，只能被子类调用
     * 传递一个线程池即可
     *
     * @param executor
     */
    public SelfSubscriber(Executor executor) {
        if (executor == null) {
            throw null;
        }
        this.executor = executor;
    }

    @Override
    public void run() {
        // 跟上次线程执行建立happens-before关系，防止多个线程并发执行
        if(on.get())
        {
            //从队列中取出信号
            Signal poll = inboundSignals.poll();
            // 根据规范条款2.8，如果当前订阅者已完成，就不需要处理了。
            if(!done)
            {
                //根据信号类型对应处理
                if(poll instanceof onNext)
                {

                }
                //订阅信号
                else if(poll instanceof  onSubscribe)
                {

                }

            }
        }
    }

    @Override
    public void onSubscribe(Subscription s) {

    }

    @Override
    public void onNext(T t) {

    }

    @Override
    public void onError(Throwable t) {

    }

    @Override
    public void onComplete() {

    }
}
