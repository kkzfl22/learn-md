package com.maxwell.datack.client;

import com.maxwell.datack.dto.PasswordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/11
 */
public class DefaultPasswordVerificationService implements PasswordVerificationService {


    /**
     * client操作
     */
    private final WebClient webClient;


    public DefaultPasswordVerificationService(WebClient.Builder weBuilder) {
        this.webClient = weBuilder
                .baseUrl("http://127.0.0.1:8080")
                .build();
    }

    @Override
    public Mono<Void> check(String raw, String encoded) {
        return webClient
                //使用post请求
                .post()
                .uri("/password")
                .body(BodyInserters.fromPublisher(Mono.just(new PasswordDTO(raw, encoded)), PasswordDTO.class))
                .retrieve()
                .toEntityFlux(ResponseEntity.class)
                .flatMap(response -> {
                    System.out.println("接收到的数据:" + response);
                    if (response.getStatusCode().is2xxSuccessful()) {
                        return Mono.empty();
                    } else if (response.getStatusCode() == HttpStatus.EXPECTATION_FAILED) {
                        return Mono.error(new BadCredentialsException("EXPECTATION_FAILED"));
                    }
                    return Mono.error(new IllegalArgumentException("default error"));
                });

    }
}
