package com.nullnull.demo.service;

import com.nullnull.demo.dto.PasswordDTO;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.server.HandlerFunction;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/10
 */
@Component
public class PasswordHandlerFunction implements HandlerFunction {

    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(5);

    @Override
    public Mono<Void> handle(ServerRequest serverRequest) {
        return serverRequest
                .bodyToMono(PasswordDTO.class)
                .map(p -> {
                            System.out.println("收到数据：" + p.getRaw() + "," + p.getSecured());
                            boolean matchRsp = passwordEncoder.matches(p.getRaw(), p.getSecured());
                            System.out.println("收到数据：" + p.getRaw() + "," + p.getSecured() + ",rsp:" + matchRsp);
                            return matchRsp;
                        }
                )
                .log()
                .flatMap(isMatched ->
                        {
                            System.out.println("结果：" + isMatched);
                            Mono<ServerResponse> rsp = isMatched
                                    ? ServerResponse.ok().build()
                                    : ServerResponse.status(HttpStatus.EXPECTATION_FAILED).build();
                            rsp.doOnNext(item -> System.out.println("rsp11" + item.statusCode().name()));
                            return rsp;
                        }
                )
                .then(Mono.empty());
    }
}
