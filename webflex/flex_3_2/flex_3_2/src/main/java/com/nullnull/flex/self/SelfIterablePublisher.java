package com.nullnull.flex.self;

import com.nullnull.flex.demo.AsyncIterablePublisher;
import org.reactivestreams.Publisher;
import org.reactivestreams.Subscriber;
import org.reactivestreams.Subscription;

import java.util.Collections;
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
        new SubscriberImpl(s).init();
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
        final long n;

        public Request(long n) {
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

        /**
         * 该订阅票据是否失效的标志
         */
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


        private void doRequest(int n) {
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
                doSend();
            }
            //其他情况，表示当前是设置了正常请求数量
            else {
                //记录下请求个数的元素
                demand += n;
                //开始向下游发送元素
                doSend();
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
                    if (!hashNext) {
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


                // 如果还有订阅者的请求。This  makes sure that rule 1.1 is upheld (sending more than was demanded)

                // If the `Subscription` is still alive and well,
                // and we have demand to satisfy, we signal ourselves to send more data

                // 如果订阅票据没有取消，还有请求，通知自己发送更多的数据
                if (!cancelled && demand > 0)
                    signal(Send.Instance);

            } catch (Throwable t) {

                // We can only get here if `onNext` or `onComplete` threw,
                // and they are not allowed to according to 2.13,
                // so we can only cancel and log here.

                // 如果到这里，只能是onNext或onComplete抛异常，只能取消。

                // Make sure that we are cancelled,
                // since we cannot do   anything else since the `Subscriber`is faulty.

                // 确保已取消，因为是Subscriber的问题
                doCancel();

                // 记录错误信息
                (new IllegalStateException(subscriber
                        + " violated the Reactive Streams rule 2.13 by throwing an exception from onNext or "
                        + "onComplete. ", t))
                        .printStackTrace(System.err);
            }
        }


        /**
         * 异常给认
         *
         * @param signal
         */
        private void signal(final Signal signal) {
            //将信号放入入栈队列
            if (inboundSignals.offer(signal)) {
                //信号放入线程成功，则调度线程进行处理
                tryScheduleToExecute();
            }
        }

        /**
         * 该方法确保订阅票据同一个时间在同一个线程运行
         * <p>
         * 规范1.3规定，调用`Subscriber`的`onSubscribe`，`onNext`，`onError`和 `onComplete`方法必须串行，不允许并发。
         */
        private final void tryScheduleToExecute() {

            // 使用原子变量进行CAS操作，成功，是说明当前线程可以处理，失败表示已经在处理了
            if (on.compareAndSet(false, true)) {
                try {
                    //向线程池中提交一个任务
                    executor.execute(this);
                    //如果不能提交线程池运行，则优雅的退出
                } catch (Throwable e) {
                    if (!cancelled) {
                        //错误不可恢复，执行取消订阅
                        doCancel();
                        try {
                            // 停止,发送error信号
                            terminateDueTo(new
                                    IllegalStateException("Publisher terminated due to unavailable Executor.",
                                    e));
                        } finally {
                            // 后续的入站信号不需要处理了，清空信号
                            inboundSignals.clear();
                            // 取消当前订阅票据，但是让该票据处于可调度状态，以防清空入站信号之后又有入站信号加入。  异步订阅者：
                            on.set(false);

                        }

                    }

                }
            }

        }


        /**
         * 规范3.5指明，Subscription.cancel方法必须及时的返回，保持调用者的响应性， 还必须是幂等的，必须是线程安全的。
         * <p>
         * 因此该方法不能执行密集的计算。
         */
        private void doCancel() {
            cancelled = true;
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
            // 与上次线程执行建立happens-before关系，防止并发执行
            // 如果on.get()为false，则不执行，线程退出
            // 如果on.get()为false，则表示没有线程在执行，当前线程可以执行
            if (on.get()) {
                //1,从队列中取出一个信号
                Signal poll = inboundSignals.poll();
                // 规范1.8：如果`Subscription`被取消了，则必须最终停止向`Subscriber`发送通知。
                // 规范3.6：如果取消了`Subscription`，则随后调用`Subscription.request( long n)`必须是无效的（NOPs）。
                // 如果订阅票据没有取消
                if (!cancelled) {
                    try {
                        //根据信号进行方法的处理操作
                        // 请求
                        if (poll instanceof Request) {
                            doRequest((int) ((Request) poll).n);
                        }
                        //发送信息
                        if (poll == Send.Instance) {
                            doSend();
                        }
                        //取消信号
                        if (poll == Cancel.Instance) {
                            doCancel();
                        }
                        //订阅信号
                        if (poll == Subscribe.Instance) {
                            doSubscribe();
                        }
                    } finally {
                        // 保证与下一个线程调度的happens-before关系
                        on.set(false);
                        //如果还有信号需要处理
                        if (!inboundSignals.isEmpty()) {
                            // 调度当前线程进行处理
                            tryScheduleToExecute();
                        }
                    }

                }
            }


        }

        /**
         * 不是在`Publisher.subscribe`方法中同步地调用`subscriber.onSubscribe` 方法，而是异步地执行subscriber.onSubscribe方法
         * <p>
         * 这样可以避免在调用线程执行用户的代码。因为在订阅者的onSubscribe方法中要执行 Iterable.iterator方法。
         * <p>
         * 异步处理也无形中遵循了规范的1.9。
         */
        private void doSubscribe() {
            try {
                //获取数据源的迭代器
                iterator = iterable.iterator();

                if (iterator == null) {
                    // 如果iterator是null，就重置为空集合的迭代器。我们假设 iterator永远不是null值。
                    iterator = Collections.<T>emptyList().iterator();
                }
            } catch (Throwable e) {
                // Publisher发生了异常，此时需要通知订阅者onError信号。
                // 但是规范1.9指定了在通知订阅者其他信号之前，必须先通知订阅者  onSubscribe信号。
                // 因此，此处通知订阅者onSubscribe信号，发送空的订阅票据
                subscriber.onSubscribe(new Subscription() {
                    @Override
                    public void request(long n) {
                        // 空的
                    }

                    @Override
                    public void cancel() {
                        // 空的
                    }
                });
                // 根据规范1.9，通知订阅者onError信号
                terminateDueTo(e);
            }

            if (!cancelled) {
                try {
                    // 为订阅者设置订阅票据。
                    subscriber.onSubscribe(this);
                } catch (Throwable e) {
                    // Publisher方法抛异常，此时需要通知订阅者onError信号。
                    // 但是根据规范2.13，通知订阅者onError信号之前必须先取消该订阅 者的订阅票据。
                    // Publisher记录下异常信息。
                    terminateDueTo(new IllegalStateException(subscriber
                            + "violated the Reactive Streams rule 2.13 by throwing an exception from onSubscribe. ",
                            e));
                }

                // 立即处理已经完成的迭代器
                boolean hashNext = false;
                try {
                    // 判断是否还有未发送的数据，如果没有，则向订阅者发送onComplete 信号
                    hashNext = iterator.hasNext();
                } catch (Throwable e) {
                    // 规范的1.4规定
                    // 如果hasNext发生异常，必须向订阅者发送onError信号，发送信号之  前先取消订阅
                    // 规范1.2规定，Publisher通过向订阅者通知onError或 onComplete信号，
                    // 发送少于订阅者请求的onNext信号。
                    terminateDueTo(e);
                }


                // 如果没有数据发送了，表示已经完成，直接发送onComplete信号终止订阅 票据。
                // 规范1.3规定，通知订阅者onXxx信号，必须串行，不能并发。
                if (!hashNext) {
                    try {
                        // 规范1.6指明，在通知订阅者onError或onComplete信号之   前，必须先取消订阅者的订阅票据。
                        // 在发送onComplete信号之前，考虑一下，有可能是  Subscription取消了订阅。
                        doCancel();
                        subscriber.onComplete();
                    } catch (final Throwable t) {
                        // 规范2.13指出，onComplete信号不允许抛异常，因此此处只能 记录下来日志
                        (new IllegalStateException(subscriber
                                + " violatedthe Reactive Streams rule 2.13 by throwing an exception from onComplete.",
                                t)).printStackTrace(System.err);
                    }
                }
            }

        }

        /**
         * 注册订阅者发送过来的信号
         *
         * @param n the strictly positive number of elements to requests to the upstream {@link Publisher}
         */
        @Override
        public void request(final long n) {
            signal(new Request(n));
        }


        @Override
        public void cancel() {
            signal(Cancel.Instance);
        }

        /**
         * init方法的设置，用于确保SubscriptionImpl实例在暴露给线程池之前已经构造完成
         * <p>
         * 因此，在构造器一完成，就调用该方法，仅调用一次。
         * <p>
         * 先发个信号试一下
         */
        void init() {
            signal(Subscribe.Instance);
        }
    }


}
