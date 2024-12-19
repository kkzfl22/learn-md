package com.nullnull.demo.client;

import com.nullnull.demo.entity.User;
import org.junit.Test;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author nullnull
 * @since 2024/12/19
 */
public class TestRequestServer {

    @Test
    public void testRun() throws InterruptedException {

        CountDownLatch latch = new CountDownLatch(1);

        WebClientAdapter server = new WebClientAdapter(WebClient.builder());

        List<Mono<User>> listResponse = new ArrayList<>();


        listResponse.add(server.adapter("/server/get1"));
        listResponse.add(server.adapter("/server/get2"));
        listResponse.add(server.adapter("/server/get3"));
        listResponse.add(server.adapter("/server/get4"));
        listResponse.add(server.adapter("/server/get5"));
        listResponse.add(server.adapter("/server/get6"));
        listResponse.add(server.adapter("/server/get7"));
        listResponse.add(server.adapter("/server/get8"));
        listResponse.add(server.adapter("/server/get9"));

        listResponse.add(server.adapter("/server/get10"));
        listResponse.add(server.adapter("/server/get11"));
        listResponse.add(server.adapter("/server/get12"));
        listResponse.add(server.adapter("/server/get13"));
        listResponse.add(server.adapter("/server/get14"));
        listResponse.add(server.adapter("/server/get15"));
        listResponse.add(server.adapter("/server/get16"));
        listResponse.add(server.adapter("/server/get17"));
        listResponse.add(server.adapter("/server/get18"));
        listResponse.add(server.adapter("/server/get19"));

        listResponse.add(server.adapter("/server/get20"));
        listResponse.add(server.adapter("/server/get21"));
        listResponse.add(server.adapter("/server/get22"));
        listResponse.add(server.adapter("/server/get23"));
        listResponse.add(server.adapter("/server/get24"));
        listResponse.add(server.adapter("/server/get25"));
        listResponse.add(server.adapter("/server/get26"));
        listResponse.add(server.adapter("/server/get27"));
        listResponse.add(server.adapter("/server/get28"));
        listResponse.add(server.adapter("/server/get29"));

        listResponse.add(server.adapter("/server/get30"));
        listResponse.add(server.adapter("/server/get31"));
        listResponse.add(server.adapter("/server/get32"));
        listResponse.add(server.adapter("/server/get33"));
        listResponse.add(server.adapter("/server/get34"));
        listResponse.add(server.adapter("/server/get35"));
        listResponse.add(server.adapter("/server/get36"));
        listResponse.add(server.adapter("/server/get37"));
        listResponse.add(server.adapter("/server/get38"));
        listResponse.add(server.adapter("/server/get39"));



        Mono.zip(listResponse, item -> {
                    return item;
                })
                .log()
                .subscribe(item -> System.out.println("结果：" + item),
                        ex -> {
                            System.err.println("error" + ex);
                            latch.countDown();
                        },
                        () -> {
                            System.out.println("成功");
                            latch.countDown();
                        }

                );

        latch.await();
    }

}
