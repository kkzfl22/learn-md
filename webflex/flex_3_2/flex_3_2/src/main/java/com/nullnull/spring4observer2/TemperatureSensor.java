package com.nullnull.spring4observer2;


import org.springframework.stereotype.Component;
import rx.Observable;

import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author nullnull
 * @since 2023/7/13
 */
@Component
public class TemperatureSensor {

    private final Random random = new Random();

    private final Observable<Temperature> dataStream = Observable.range(0, Integer.MAX_VALUE)
            .concatMap(tick -> Observable.just(tick)
                    //延迟
                    .delay(random.nextInt(5000), TimeUnit.MILLISECONDS)
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

