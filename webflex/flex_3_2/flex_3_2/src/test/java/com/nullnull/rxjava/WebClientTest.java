package com.nullnull.rxjava;

import org.junit.Test;
import org.springframework.http.MediaType;
import org.springframework.http.client.reactive.ClientHttpConnector;
import org.springframework.http.client.reactive.ReactorClientHttpConnector;
import org.springframework.web.reactive.function.client.WebClient;

import java.net.URI;

/**
 * @author nullnull
 * @since 2023/7/15
 */
public class WebClientTest {


    @Test
    public void httpClient() throws InterruptedException {
        ClientHttpConnector connector = new ReactorClientHttpConnector();

        WebClient.builder().clientConnector(connector).build()
                //获取
                .get()
                //.uri(URI.create("https://edu.lagou.com"))
                .uri(URI.create("https://www.baidu.com"))
                .accept(MediaType.TEXT_HTML)
                .retrieve()
                .bodyToMono(String.class)
                .subscribe(System.out::println);

        Thread.sleep(3000);
    }

}
