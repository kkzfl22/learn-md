package com.nullnull.spring4observer2;

import org.json.JSONObject;
import org.springframework.web.servlet.mvc.method.annotation.SseEmitter;
import rx.Subscriber;

import java.io.IOException;

/**
 * RxSseEmitter 负责从Subscriber接收到的数据转换为JSON响应客户端
 * @author nullnull
 * @since 2023/7/15
 */
public class RxSseEmitter extends SseEmitter {
    static final long SSE_SESSION_TIMEOUT = 30 * 60 * 1000L;
    private final Subscriber<Temperature> subscriber;

    public RxSseEmitter() {
        super(SSE_SESSION_TIMEOUT);
        //订阅一个流
        this.subscriber = new Subscriber<Temperature>() {
            @Override
            public void onNext(Temperature temperature) {
                try {
                    //将数据转换为JSON对象
                    final JSONObject jsonObject = new JSONObject(temperature);
                    final String temperatureJson = jsonObject.toString();
                    System.out.println(temperatureJson);
                    //数据输出
                    RxSseEmitter.this.send(temperatureJson);
                } catch (IOException e) {
                    unsubscribe();
                }
            }

            @Override
            public void onError(Throwable e) {
                System.err.println(e);
            }

            @Override
            public void onCompleted() {
                System.out.println("job done");
            }
        };
        onCompletion(subscriber::unsubscribe);
        onTimeout(subscriber::unsubscribe);
    }


    public Subscriber<Temperature> getSubscriber() {
        return subscriber;
    }
}
