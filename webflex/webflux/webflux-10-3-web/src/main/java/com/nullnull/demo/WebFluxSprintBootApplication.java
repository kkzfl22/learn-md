package com.nullnull.demo;

import com.nullnull.demo.service.PasswordHandlerFunction;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.security.web.server.SecurityWebFilterChain;;

/**
 * @author nullnull
 * @since 2024/12/10
 */
@SpringBootApplication
public class WebFluxSprintBootApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxSprintBootApplication.class, args);
    }


    @Bean
    public static RouterFunction<ServerResponse> routes(PasswordHandlerFunction handlerFunction) {
        return RouterFunctions.route(RequestPredicates.POST("/password"), handlerFunction);
    }


    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity serverHttpSecurity) {
        return serverHttpSecurity.csrf().disable().build();
    }


}
