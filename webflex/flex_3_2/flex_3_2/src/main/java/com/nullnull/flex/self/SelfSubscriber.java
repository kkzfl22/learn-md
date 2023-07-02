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
public abstract class SelfSubscriber<T> implements Subscriber<T>, Runnable {

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
        public final Throwable error;

        public onError(Throwable error) {
            this.error = error;
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
     * This method is invoked when the OnNext signals arrive
     * Returns whether more elements are desired or not, and if no more elements are   desired,
     * for convenience.
     *
     * @param element
     * @return
     */
    protected abstract boolean whenNext(final T element);

    /**
     * This method is invoked when the OnComplete signal arrives
     * override this method to implement your own custom onComplete logic.
     */
    protected abstract void whenOnComplete();


    /**
     * This method is invoked if the OnError signal arrives
     * override this method to implement your own custom onError logic.
     */
    protected abstract void whenOnError(Throwable e);


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
        if (on.get()) {
            try {
                //从队列中取出信号
                Signal poll = inboundSignals.poll();
                // 根据规范条款2.8，如果当前订阅者已完成，就不需要处理了。
                if (!done) {
                    //根据信号类型对应处理
                    if (poll instanceof onNext) {
                        handleOnNext(((onNext<T>) poll).next);
                    }
                    //订阅信号
                    else if (poll instanceof onSubscribe) {
                        handleOnSubscribe(((onSubscribe) poll).subscription);
                    }
                    //错误信号
                    else if (poll instanceof onError) {
                        handleOnError(((onError) poll).error);
                    }
                    //完成信号
                    else if (poll instanceof onComplete) {
                        handleOnComplete();
                    }
                }
            } finally {
                //设置为false，让下一个线程可以调度。
                on.set(false);
                //队列中还存在数据，继续执行入队列信号
                if (!inboundSignals.isEmpty()) {
                    tryScheduleToExecute();
                }
            }
        }
    }

    /**
     * 调度放入线程池处理操作
     * 确保订阅者一次仅在一个线程执行
     */
    private void tryScheduleToExecute() {
        //原子CAS操作，将false改为true
        if (on.compareAndSet(false, true)) {
            try {
                //将任务提交线程池
                executor.execute(this);
            } catch (Throwable e) {
                // 根据规范条款2.13，如果不能执行线程池的提交方法，需要优雅退出
                if (!done) {
                    try {
                        // 由于错误不可恢复，因此取消订阅票据
                        done();
                    } finally {
                        //首先做队列的清空处理
                        inboundSignals.clear();
                        // 由于订阅票据已经取消，但是此处依然让订阅者处于可调度的状
                        //态，以防在清空入站信号之后又有信号发送过来
                        // 因为信号的发送是异步的
                        on.set(false);
                    }
                }
            }
        }
    }

    /**
     * 幂等地标记当前订阅者已完成处理，不再处理更多的元素。
     * 因此，需要取消订阅票据（Subscription）
     */
    private void done() {
        // 在此处，可以添加done，对订阅者的完成状态进行设置；
        // 虽然规范3.7规定Subscription.cancel()是幂等的，我们不需要这么做。
        // 当whenNext方法抛异常，认为订阅者已经处理完成（不再接收更多元素）
        done = true;
        // If we are bailing out before we got a `Subscription`there 's little need for cancelling it.
        if (subscription != null) {
            try {
                subscription.cancel();
            } catch (Throwable e) {
                // 根据规范条款3.15，此处不能抛异常，因此只是记录下来。
                (new IllegalStateException(subscription
                        + " violated the Reactive Streams rule 3.15 by throwing an exception from cancel.",
                        e)).printStackTrace(System.err);
            }
        }
    }

    /**
     * 完成信号处理
     */
    private void handleOnComplete() {
    }

    /**
     * 错误信号处理
     *
     * @param error
     */
    private void handleOnError(Throwable error) {
    }

    /**
     * 订阅信号处理
     *
     * @param subscription
     */
    private void handleOnSubscribe(Subscription subscription) {
    }

    /**
     * 处理数据
     *
     * @param element
     */
    private void handleOnNext(final T element) {
        // If we aren't already done
        if (done) {
            return;
        }
        // Technically this check is not
        if (subscription == null) {
            //needed, since we are expecting Publishers to conform to the spec
            //        // Check for spec violation of 2.1 and 1.09
            (new IllegalStateException(
                    "Someone violated the Reactive Streams rule 1.09 and 2.1 by signalling OnNext before  "
                            + "`Subscription.request`. (no Subscription)")).printStackTrace(System.err);
        }

        //
        if(whenNext(element))
        {

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
