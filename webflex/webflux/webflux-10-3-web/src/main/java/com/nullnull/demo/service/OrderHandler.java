package com.nullnull.demo.service;

import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/10
 */
@Service
public class OrderHandler {


    public Mono<ServerResponse> get(ServerRequest request) {
        return Mono.empty();
    }


    public Mono<ServerResponse> list(ServerRequest request) {
        return Mono.empty();
    }

    public Mono<ServerResponse> create(ServerRequest request) {
        return Mono.empty();
    }
}
