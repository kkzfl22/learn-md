package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.FluxSink;
import reactor.core.publisher.SynchronousSink;
import reactor.util.function.Tuples;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import java.util.concurrent.Callable;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.stream.IntStream;

/**
 * @author nullnull
 * @since 2024/12/7
 */
public class TestFluxPush {

    @Test
    public void push() throws InterruptedException {
        //将命令式流转换为响应式流,此处只能是单线程的
        Flux.push(new Consumer<FluxSink<Integer>>() {
                    @Override
                    public void accept(FluxSink<Integer> integerFluxSink) {
                        //通过单线程的方式，调用其他方法，其他方法的返回值，可以通过FluxSink追加到下游的响应式流中
                        IntStream.range(1, 100).forEach((t) -> integerFluxSink.next(t));
                    }
                }).delayElements(Duration.ofMillis(200))
                .subscribe(item -> System.out.println("next:" + item),
                        e -> System.out.println("e:" + e),
                        () -> System.out.println("finish"));

        Thread.sleep(2000);
    }

    @Test
    public void pushLambda() throws InterruptedException {
        Flux.push(fluxSink -> IntStream.range(1, 100).forEach(fluxSink::next))
                //延迟200毫秒
                .delayElements(Duration.ofMillis(200))
                //订阅
                .subscribe(item -> System.out.println("next:" + item),
                        e -> System.out.println("e:" + e),
                        () -> System.out.println("finish"));

        Thread.sleep(2000);
    }

    /**
     * 事件处理器
     */
    static class MyEventProcess {
        private MyEventListener listener;
        private Random rand = new Random();

        /**
         * 注册回调的事件监听器
         *
         * @param listener
         */
        void register(MyEventListener listener) {
            this.listener = listener;
        }

        public void process() {
            //构建数据，向下游发送
            while (rand.nextInt() % 3 != 0) {
                List<String> dataChunk = new ArrayList<>();
                for (int i = 0; i < 10; i++) {
                    dataChunk.add("data-" + i);
                }
                //发送数据
                listener.onDataChunk(dataChunk);
            }
            //完成信号
            listener.processComplete();
        }
    }

    /**
     * 回调接口
     *
     * @param <T>
     */
    interface MyEventListener<T> {
        /**
         * 传递数据的信号
         *
         * @param chunk
         */
        void onDataChunk(List<T> chunk);

        /**
         * 处理数据的信号
         */
        void processComplete();
    }

    @Test
    public void create() throws InterruptedException {
        MyEventProcess process = new MyEventProcess();
        Flux<Object> objectFlux = Flux.create(fluxSink -> {
            //注册监听器
            process.register(new MyEventListener<String>() {
                @Override
                public void onDataChunk(List<String> chunk) {
                    //将数据发送到下游的响应式流中
                    for (String chunkItem : chunk) {
                        fluxSink.next(chunkItem);
                    }
                }

                @Override
                public void processComplete() {
                    //结束流的发送
                    fluxSink.complete();
                }
            });
        });
        objectFlux.subscribe(item -> System.out.println("item:" + item),
                e -> System.out.println("e" + e),
                () -> System.out.println("完成")
        );

        //开始生成数据
        process.process();
        Thread.sleep(5000);
    }


    @Test
    public void generate() throws InterruptedException {
        Flux.generate(
                        //用于构建初始值
                        new Callable<ArrayList<Long>>() {
                            @Override
                            public ArrayList<Long> call() throws Exception {
                                final ArrayList<Long> dataList = new ArrayList<>();
                                dataList.add(0L);
                                dataList.add(1L);

                                return dataList;
                            }
                        },//函数的处理逻辑
                        new BiFunction<ArrayList<Long>, SynchronousSink<Long>, ArrayList<Long>>() {
                            @Override
                            public ArrayList<Long> apply(ArrayList<Long> list, SynchronousSink<Long> sink) {
                                final Long getLong1 = list.get(list.size() - 1);
                                final Long getLong2 = list.get(list.size() - 2);
                                //向下游发送元素
                                sink.next(getLong1);

                                list.add(getLong1 + getLong2);

                                return list;
                            }
                        }

                ).delayElements(Duration.ofMillis(200))
                .take(10)
                .subscribe(
                        item -> System.out.println("next:" + item),
                        ex -> System.out.println("error:" + ex),
                        () -> System.out.println("完成")
                );

        Thread.sleep(3000L);
    }

    @Test
    public void generateLambda() throws InterruptedException {
        Flux.generate(
                        () -> {
                            final ArrayList<Long> dataList = new ArrayList<>();
                            dataList.add(0L);
                            dataList.add(1L);

                            return dataList;
                        },
                        (list, sink) -> {
                            final Long getLong1 = list.get(list.size() - 1);
                            final Long getLong2 = list.get(list.size() - 2);
                            //向下游发送元素
                            sink.next(getLong1);

                            list.add(getLong1 + getLong2);

                            return list;
                        }

                ).delayElements(Duration.ofMillis(200))
                .take(10)
                .subscribe(
                        item -> System.out.println("next:" + item),
                        ex -> System.out.println("error:" + ex),
                        () -> System.out.println("完成")
                );

        Thread.sleep(3000L);
    }

    @Test
    public void generateTuples() throws InterruptedException {
        Flux.generate(() -> Tuples.of(0, 1),
                        (tuples, sink) -> {
                            sink.next(tuples.getT2());
                            int sumValue = tuples.getT1() + tuples.getT2();
                            return Tuples.of(tuples.getT2(), sumValue);
                        })
                .delayElements(Duration.ofMillis(300))
                .take(10)
                .subscribe(System.out::println);
        Thread.sleep(5000);
    }

}
