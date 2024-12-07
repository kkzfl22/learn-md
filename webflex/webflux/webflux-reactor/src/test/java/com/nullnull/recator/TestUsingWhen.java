package com.nullnull.recator;

import org.junit.Test;
import org.reactivestreams.Publisher;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.Random;
import java.util.function.BiFunction;
import java.util.function.Function;

/**
 * 使用响应式流包装响应式事务
 *
 * @author nullnull
 * @since 2024/12/7
 */
public class TestUsingWhen {


    public static class Transaction {
        private static final Random rand = new Random();
        private final int id;

        /**
         * 创建事务
         *
         * @param id
         */
        public Transaction(int id) {
            this.id = id;
        }

        /**
         * 开启响应式事务
         *
         * @return
         */
        public static Mono<Transaction> beginTransaction() {
            System.out.println("开启事务");
            return Mono.defer(() -> Mono.just(new Transaction(rand.nextInt())));
        }

        /**
         * 响应式插入数据
         *
         * @param rows
         * @return
         */
        public Flux<String> insertRow(Publisher<String> rows) {
            return Flux.from(rows)
                    .delayElements(Duration.ofMillis(300))
                    //将数据转换为响应式流
                    .flatMap(row -> {
                        if (rand.nextInt(10) < 2) {
                            return Mono.error(new IllegalArgumentException("出错条目:" + row));
                        } else {
                            return Mono.just(row);
                        }
                    });
        }

        /**
         * 提交事务
         *
         * @return
         */
        public Mono<Void> commit() {
            return Mono.defer(() -> {
                System.out.println("开始提交事务:");
                if (rand.nextBoolean()) {
                    return Mono.empty();
                } else {
                    return Mono.error(new RuntimeException("事务提交异常"));
                }
            });
        }

        /**
         * 响应式事务的回滚
         *
         * @return
         */
        public Mono<Void> rollback() {
            return Mono.defer(() -> {
                System.out.println("开始提交事务");
                if (rand.nextBoolean()) {
                    return Mono.empty();
                } else {
                    return Mono.error(new RuntimeException("回滚异常"));
                }
            });
        }
    }

    /**
     * 使用usingWhen实现一个更新的事务
     */
    @Test
    public void usingWhen() throws InterruptedException {
        Flux.usingWhen(
                //提供资源
                Transaction.beginTransaction(),
                //插入数据
                new Function<Transaction, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Transaction transaction) {
                        System.out.println("插入数据");
                        return transaction.insertRow(Flux.just("a", "b", "c"));
                    }
                },
                //正常提交事务
                new Function<Transaction, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Transaction transaction) {
                        System.out.println("提交事务");
                        return transaction.commit();
                    }
                },
                //发生异常时，清理资源
                new BiFunction<Transaction, Throwable, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Transaction transaction, Throwable throwable) {
                        System.out.println("回滚事务");
                        return transaction.rollback();
                    }
                },
                //如果查询取消，清理资源
                new Function<Transaction, Publisher<?>>() {
                    @Override
                    public Publisher<?> apply(Transaction transaction) {
                        System.out.println("取消查询");
                        return null;
                    }
                }
        ).subscribe(event -> System.out.println("onNext:" + event),
                e -> System.out.println("error:" + e),
                () -> System.out.println("finish")
        );
        Thread.sleep(5000);
    }

    @Test
    public void usingWhenLambda() throws InterruptedException {
        Flux.usingWhen(
                //提供资源
                Transaction.beginTransaction(),
                //资源的使用，即插入数据
                transaction -> transaction.insertRow(Flux.just("a", "b", "c")),
                //提交事务
                transaction -> transaction.commit(),
                //发生异常，清理资源
                (transaction, throwable) -> transaction.rollback(),
                transaction -> transaction.rollback()

        ).subscribe(event -> System.out.println("onNext:" + event),
                e -> System.out.println("error:" + e),
                () -> System.out.println("finish")
        );
        Thread.sleep(2000);
    }
}
