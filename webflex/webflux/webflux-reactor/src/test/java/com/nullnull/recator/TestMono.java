package com.nullnull.recator;

import org.junit.Test;
import reactor.core.publisher.Mono;

import javax.swing.text.html.Option;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.Optional;

/**
 * 使用Mono类型
 *
 * @author nullnull
 * @since 2024/12/2
 */
public class TestMono {

    @Test
    public void just() {
        Mono<String> just = Mono.just("nullnull");
        just.subscribe(System.out::println);
    }

    @Test
    public void justOrEmpty() {
        Mono<String> objectMono = Mono.justOrEmpty(null);
        objectMono.subscribe(System.out::println);
    }

    @Test
    public void justOrEmptyOption() {
        Mono<String> objectMono = Mono.justOrEmpty(Optional.empty());
        objectMono.subscribe(System.out::println);
    }


    @Test
    public void monoHttpRequest() throws IOException {
        Mono.fromCallable(() -> httpRequest()).subscribe(System.out::println);
    }

    @Test
    public void monoHttpRequestLambda() {
        Mono.fromCallable(this::httpRequest).subscribe(System.out::println);
    }

    /**
     * 异常及完成信号处理
     */
    @Test
    public void monoHttpRequestLambdaFull() {
        Mono.fromCallable(this::httpRequest).subscribe(
                item -> System.out.println(item),
                ex -> System.out.println("异常信息:" + ex.toString()),
                () -> System.out.println("请求完成")
        );
    }

    private String httpRequest() throws IOException {
        URL url = new URL("https://www.baidu.com");
        URLConnection urlConnection = url.openConnection();
        urlConnection.connect();

        try (InputStream inputStream = urlConnection.getInputStream();
             InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
             BufferedReader bufferedReader = new BufferedReader(inputStreamReader);) {
            String tmp = null;

            StringBuilder result = new StringBuilder();

            while ((tmp = bufferedReader.readLine()) != null) {
                result.append(tmp).append("\n");
            }

            return result.toString();
        } catch (IOException e) {
            throw e;
        }
    }


}
