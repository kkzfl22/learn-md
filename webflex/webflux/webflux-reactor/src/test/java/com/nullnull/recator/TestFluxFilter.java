package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

/**
 * 过滤响应式流
 *
 * @author nullnull
 * @since 2024/12/4
 */
public class TestFluxFilter {


    @Test
    public void testStartStop() throws InterruptedException {
        System.out.println("start");
        //延迟1秒后开始触发
        Mono<String> start = Mono.just("start").delayElement(Duration.ofSeconds(1));
        //在延迟4秒后停止
        Mono<String> stop = Mono.just("stop").delayElement(Duration.ofSeconds(4));
        Flux.interval(Duration.ofMillis(500))
                .map(item -> "fluxElement:" + item)
                .skipUntilOther(start)
                .takeUntilOther(stop)
                .subscribe(System.out::println);
        Thread.sleep(6000);

        System.out.println("finish");
    }

    @Test
    public void testCollectSortedList() {
        Flux.just(1, 3, 2, 5, 6, 2, 4, 1)
                .collectSortedList(Comparator.reverseOrder())
                .subscribe(System.out::println);
    }

    @Test
    public void testCollectMap() {
        Flux.just(1, 2, 3, 4, 5, 6)
                .collectMap(new Function<Integer, String>() {
                    @Override
                    public String apply(Integer integer) {
                        return "key:" + integer;
                    }
                })
                .subscribe(System.out::println);
    }

    @Test
    public void testCollectMap1() {
        Flux.just(1, 2, 3, 4, 5, 6)
                .collectMap(item -> "key:" + item)
                .subscribe(System.out::println);
    }

    @Test
    public void testCollectMap2() {
        Flux.just(1, 2, 3, 4, 5, 6)
                .collectMap(item -> "key:" + item, value -> "value:" + value)
                .subscribe(System.out::println);
    }

    @Test
    public void testCollectMap3() {
        Flux.just(1, 2, 3, 4, 5, 6)
                .collectMap(item -> "key:" + item, value -> "value:" + value, () -> {
                    Map<String, String> result = new HashMap<>();
                    for (int i = 0; i < 3; i++) {
                        result.put("ik-" + i, "iv" + i);
                    }
                    return result;
                })
                .subscribe(System.out::println);
    }

    @Test
    public void testCollectMultiMap() {
        Flux.just(1, 2, 3)
                .collectMultimap(key -> "key:" + key, value -> {
                    List<String> valueResult = new ArrayList<>();
                    for (int i = 0; i < value; i++) {
                        valueResult.add("vs:" + i);
                    }
                    return valueResult;
                }).subscribe(System.out::println);
    }

    @Test
    public void testCollectMultiMap2() {
        Flux.just(1, 2, 3)
                .collectMultimap(key -> "key:" + key, value -> {
                            List<String> valueResult = new ArrayList<>();
                            for (int i = 0; i < value; i++) {
                                valueResult.add("vs:" + i);
                            }
                            return valueResult;
                        },
                        () -> {
                            Map map = new HashMap<String, List<String>>();
                            for (int i = 0; i < 2; i++) {
                                map.put("add:" + i, Arrays.asList("addv" + i));
                            }
                            return map;
                        }

                ).subscribe(System.out::println);
    }

    @Test
    public void repeat() {
        Flux.just(1, 2, 3)
                //此数据会打印3遍，1次原始数据，2次重复数据
                .repeat(2)
                .subscribe(System.out::println);
    }

    @Test
    public void defaultIfEmpty() {
        Flux.empty().defaultIfEmpty("nullnull").subscribe(System.out::println);
    }


    @Test
    public void distinct() {
        Flux.just(1, 2)
                .repeat(2)
                .distinct()
                .subscribe(System.out::println);
    }


    @Test
    public void distinctUntilChanged() {
        Flux.just(1, 2)
                .repeat(2)
                .distinctUntilChanged()
                .subscribe(System.out::println);
    }


    @Test
    public void distinctUntilChanged2() {
        Flux.just(1, 1, 2, 2, 3, 3, 1, 1)
                .distinctUntilChanged()
                .subscribe(System.out::println);

    }


    @Test
    public void any() {
        Flux.just(1, 2, 3)
                //检查是否包含偶数
                .any(item -> item % 2 == 0)
                .subscribe(System.out::println);
    }


    @Test
    public void reduce() {
        Flux.range(1, 5)
                .reduce(0, (item1, item2) ->
                        {
                            System.out.println("item1:" + item1 + ",item2:" + item2);
                            return item1 + item2;
                        }
                )
                .subscribe(System.out::println);
    }


    @Test
    public void scan() {
        Flux.range(1, 5)
                .scan(0, (num1, num2) -> num1 + num2)
                .subscribe(System.out::println);
    }

    @Test
    public void scanAvg() {
        int arrLength = 3;
        Flux.range(1, 10)
                .index()
                //scan第一个发射的元素是它的初始值
                .scan(new int[arrLength], (arr, entry) -> {
                    arr[(int) (entry.getT1() % arrLength)] = entry.getT2();
                    return arr;
                })
                //当窗口的数据被填满后，才开始计算平均值
                .skip(arrLength)
                .map(array -> Arrays.stream(array).sum() * 1.0 / arrLength)
                .subscribe(System.out::println);
    }


    @Test
    public void thenMany() {
        Flux.just(1, 2, 3)
                .doOnNext(item -> System.out.println("输出：" + item))
                .thenMany(Flux.just(4, 5, 6))
                .subscribe(System.out::println);
    }


}
