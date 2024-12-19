package com.nullnull.demo.client;


import com.google.gson.Gson;
import com.nullnull.demo.entity.User;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author nullnull
 * @since 2024/12/11
 */
public class WebClientAdapter {


    /**
     * client操作
     */
    private final WebClient webClient;


    private final User EMTPYUSER = new User();


    private Gson gson = new Gson();

    public WebClientAdapter(WebClient.Builder weBuilder) {
        this.webClient = weBuilder
                .baseUrl("http://localhost:8080")
                .build();
    }


    public Mono<User> adapter(String uri) {
        return webClient
                //使用post请求
                .post()
                .uri(uri)
                .header(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE)
                .bodyValue(gson.toJson(buildUser()))
                .retrieve()
                .bodyToMono(User.class)
                .onErrorReturn(EMTPYUSER)
                ;


    }


    private User buildUser() {
        User userInfo = new User();
        userInfo.setId(UUID.randomUUID().toString());
        userInfo.setName("name-" + userInfo.getId());
        userInfo.setAge(ThreadLocalRandom.current().nextInt());
        userInfo.setAddress(UUID.randomUUID().toString());
        return userInfo;
    }
}
