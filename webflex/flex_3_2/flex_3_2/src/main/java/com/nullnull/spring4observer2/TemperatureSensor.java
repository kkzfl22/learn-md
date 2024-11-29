package com.nullnull.spring4observer2;


import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * 业务实现类
 *
 * @author nullnull
 * @since 2023/7/13
 */
@Component
public class TemperatureSensor {
    /**
     * 随机数生成对象
     */
    private final Random random = new Random();

    private final Observable<Temperature> dataStream = Observable
            //随机生成0-int最大值的数
            .range(0, Integer.MAX_VALUE)
            //对以接收到的int数据做处理，Observable
            .concatMap(tick -> Observable.just(tick)
                    //随机延迟5秒内
                    .delay(random.nextInt(5000), TimeUnit.MILLISECONDS)
                    //将随机的流返回为Temperature对象
                    .map(tickKey -> this.probe())
                    //生成生产者
                    .publish()
                    // — 让一个可连接的Observable表现得像一个普通的Observable
                    .refCount()
            );

    private Temperature probe() {
        return new Temperature(16 + random.nextGaussian() * 10);
    }

    public Observable<Temperature> temperatureStream() {
        return dataStream;
    }
}

