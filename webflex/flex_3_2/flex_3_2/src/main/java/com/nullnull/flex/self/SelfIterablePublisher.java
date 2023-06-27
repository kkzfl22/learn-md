package com.nullnull.flex.self;

import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Iterator;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Publisher的实现，用于进行数据的生产操作，可以理解为要加工的数据
 *
 * @author liujun
 * @since 2023/6/27
 */
public class SelfIterablePublisher<T> implements Publisher<T> {


    /**
     * 默认的批次大小
     */
    private static final int DEFAULT_BATCH_SIZE = 1024;

    /**
     * 迭代器，用于原始数据的生成操作
     */
    private final Iterable<T> iterable;

    /**
     * 线程池接口，用于生成任务的并行执行
     */
    private final Executor executor;

    /**
     * 用于批次处理任务的大小
     */
    private final int batchSize;


    public SelfIterablePublisher(Iterable<T> iterable, Executor executor) {
        this(iterable, executor, DEFAULT_BATCH_SIZE);
    }

    public SelfIterablePublisher(Iterable<T> iterable, Executor executor, int batchSize) {
        //进行参数的检查
        if (null == iterable) {
            throw new NullPointerException("iterable is null");
        }

        if (null == executor) {
            throw new NullPointerException("executor is null");
        }

        if (batchSize < 1) {
            throw new IllegalArgumentException("param batch size is < 1");
        }


        this.iterable = iterable;
        this.executor = executor;
        this.batchSize = batchSize;
    }

    @Override
    public void subscribe(Subscriber<? super T> s) {

    }


    /**
     * 用于处理订阅的信号
     */
    static interface Signal {
    }

    /**
     * 取消订阅的信号
     */
    enum Cancel implements Signal {
        Instance;
    }

    /**
     * 订阅的信号
     */
    enum Subscribe implements Signal {
        Instance;
    }

    /**
     * 发送的信号
     */
    enum Send implements Signal {
        Instance;
    }

    /**
     * 请求的信号
     */
    static final class Request implements Signal {
        final int n;

        public Request(int n) {
            this.n = n;
        }
    }

    /**
     * 订阅票据，实现了Subscription接口和Runnable接口
     */
    final class SubscriberImpl implements Subscription, Runnable {

        /**
         * Subscriber的引用，用于通信
         */
        private final Subscriber<? super T> subscriber;


        // 该订阅票据是否失效的标志
        private boolean cancelled = false;

        /**
         * 记录订阅者的数量，这些请求还没有对订阅者回复
         */
        private long demand = 0;

        /**
         * 发送给订阅者的数据流
         */
        private Iterator<T> iterator;

        /**
         * 该队列用于记录发送票据的信号（入栈信号），如"request"，"cancel"等。
         * <p>
         * 通过此队列可以在Publisher端使用多线程异步处理
         */
        private final ConcurrentLinkedQueue<Signal> inboundSignals = new ConcurrentLinkedQueue<>();


        /**
         * 确保票据不会并发的标志，
         * <p>
         * 防止在调用订阅者的onXxx方法的时候并发调用。规范1.3规定的不能并发。
         */
        private final AtomicBoolean on = new AtomicBoolean(false);

        public SubscriberImpl(Subscriber<? super T> subscriber) {
            //根据规范，如果Subscriber为null，需要抛空指针异常，此处抛null。
            if (null == subscriber) {
                throw null;
            }

            this.subscriber = subscriber;
        }


        /**
         * 注册订阅者发送过来的信号
         *
         * @param n the strictly positive number of elements to requests to the upstream {@link Publisher}
         */
        @Override
        public void request(final long n) {
            // 规范规定，如果请求的元素个数小于1，则抛异常
            // 并在异常信息中指明错误的原因：n必须是正整数。
            if (n < 1) {
                terminateDueTo(new IllegalArgumentException(subscriber
                        + "violated the Reactive Streams rule 3.9 by requesting a non -positive number of elements."));
            }
            // 根据规范 3.17，当请求的元素数大于Long.MAX_VALUE的时候，将请求数  设置为Long.MAX_VALUE即可。
            else if (demand + n < 1) {
                //此认为是无界流
                demand = Long.MAX_VALUE;

                //开始向下游发送元素

            }


        }

        private void doSend() {
            try {
                long leftBatchSize = demand;
                //为充分利用线程池，最多发送BatchSize个元素。然后放弃当前线程，重新调度，通知订阅者onNext信号
                do {
                    T next;
                    boolean hashNext;

                    try {
                        //订阅者在订阅的时候，已经调用了hashNext方法，直接获取
                        // Need to keep track of End-of-Stream
                        next = iterator.next();
                        //检查还有没有数据，如果没有了，表示流结束了
                        hashNext = iterator.hasNext();
                    } catch (final Throwable e) {
                        // If `next` or `hasNext` throws (they can, since  it is user - provided),we need to treat
                        // the stream as errored as per rule1.4

                        //如果next方法或者hashNext方法抛出异常（用户提供），认为流招聘了异常了发送onError信号
                        terminateDueTo(e);

                        return;
                    }

                    //向下流订阅者发送next的信号
                    subscriber.onNext(next);

                    //如果已经到达结束位置，
                    if(hashNext)
                    {
                        // We need to consider this `Subscription` as cancelled as per rule 1.6
                        // 首先考滤票据取消了订阅
                        doCancel();

                        // Then we signal `onComplete` as per rule 1.2 and    1.5
                        //发送onComplete信号给订阅者
                        subscriber.onComplete();
                    }

                } while (
                    //确保当前没有被取消订阅
                    // This makes  sure that rule 1.8 is upheld,
                    // i.e.we need to stop signalling "eventually"
                        !cancelled
                                //并且还有剩余的元素
                                // 如果还有剩余批次的元素。This   makes sure that we only send
                                // `batchSize`number of elements in one go (so  we can yield to other Runnables)
                                && --leftBatchSize > 0
                                // 如果还有订阅者的请求。This  makes sure that rule 1.1 is upheld (sending more than was demanded)
                                && --demand > 0);

            } catch (Throwable e) {

            }

            int leftBatchSize = batchSize;


        }

        private void doCancel() {

        }


        /**
         * 终止订阅，
         * <p>
         * 规范1.6指出，`Publisher`在通知订阅者`onError`或者`onComplete`信号之
         * 前，
         * <p>
         * **必须**先取消订阅者的订阅票据（`Subscription`）。
         * <p>
         * <p>
         * <p>
         * 当发送onError信号之前先取消订阅
         *
         * @param exception
         */
        private void terminateDueTo(Throwable exception) {
            //发送error前，必须取消订阅
            cancelled = true;

            try {
                //给下游发送onError信号
                subscriber.onError(exception);
            } catch (Throwable e) {
                // 规范1.9指出，onError不能抛异常。
                // 如果onError抛异常，只能记录信息。
                (new IllegalStateException(
                        subscriber +
                                "violated the  Reactive Streams rule 2.13 by throwing an exception from onError.", e))
                        .printStackTrace(System.err);
            }
        }


        @Override
        public void run() {

        }


        @Override
        public void cancel() {

        }
    }


}
