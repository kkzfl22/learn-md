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
        listResponse.add(server.adapter("/server/get40"));
        listResponse.add(server.adapter("/server/get41"));
        listResponse.add(server.adapter("/server/get42"));
        listResponse.add(server.adapter("/server/get44"));
        listResponse.add(server.adapter("/server/get44"));
        listResponse.add(server.adapter("/server/get45"));
        listResponse.add(server.adapter("/server/get46"));
        listResponse.add(server.adapter("/server/get47"));
        listResponse.add(server.adapter("/server/get48"));
        listResponse.add(server.adapter("/server/get49"));

        listResponse.add(server.adapter("/server/get50"));
        listResponse.add(server.adapter("/server/get51"));
        listResponse.add(server.adapter("/server/get52"));
        listResponse.add(server.adapter("/server/get55"));
        listResponse.add(server.adapter("/server/get55"));
        listResponse.add(server.adapter("/server/get55"));
        listResponse.add(server.adapter("/server/get56"));
        listResponse.add(server.adapter("/server/get57"));
        listResponse.add(server.adapter("/server/get58"));
        listResponse.add(server.adapter("/server/get59"));
        listResponse.add(server.adapter("/server/get60"));
        listResponse.add(server.adapter("/server/get61"));
        listResponse.add(server.adapter("/server/get62"));
        listResponse.add(server.adapter("/server/get66"));
        listResponse.add(server.adapter("/server/get66"));
        listResponse.add(server.adapter("/server/get66"));
        listResponse.add(server.adapter("/server/get66"));
        listResponse.add(server.adapter("/server/get67"));
        listResponse.add(server.adapter("/server/get68"));
        listResponse.add(server.adapter("/server/get69"));
        listResponse.add(server.adapter("/server/get70"));
        listResponse.add(server.adapter("/server/get71"));
        listResponse.add(server.adapter("/server/get72"));
        listResponse.add(server.adapter("/server/get77"));
        listResponse.add(server.adapter("/server/get77"));
        listResponse.add(server.adapter("/server/get77"));
        listResponse.add(server.adapter("/server/get77"));
        listResponse.add(server.adapter("/server/get77"));
        listResponse.add(server.adapter("/server/get78"));
        listResponse.add(server.adapter("/server/get79"));
        listResponse.add(server.adapter("/server/get80"));
        listResponse.add(server.adapter("/server/get81"));
        listResponse.add(server.adapter("/server/get82"));
        listResponse.add(server.adapter("/server/get88"));
        listResponse.add(server.adapter("/server/get88"));
        listResponse.add(server.adapter("/server/get88"));
        listResponse.add(server.adapter("/server/get88"));
        listResponse.add(server.adapter("/server/get88"));
        listResponse.add(server.adapter("/server/get88"));
        listResponse.add(server.adapter("/server/get89"));
        listResponse.add(server.adapter("/server/get90"));
        listResponse.add(server.adapter("/server/get91"));
        listResponse.add(server.adapter("/server/get92"));
        listResponse.add(server.adapter("/server/get99"));
        listResponse.add(server.adapter("/server/get99"));
        listResponse.add(server.adapter("/server/get99"));
        listResponse.add(server.adapter("/server/get99"));
        listResponse.add(server.adapter("/server/get99"));
        listResponse.add(server.adapter("/server/get99"));
        listResponse.add(server.adapter("/server/get99"));


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
