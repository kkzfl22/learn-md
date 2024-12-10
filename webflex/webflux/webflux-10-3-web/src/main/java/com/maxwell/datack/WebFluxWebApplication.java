package com.maxwell.datack;

import com.maxwell.datack.service.OrderHandler;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;


/**
 * @author nullnull
 * @since 2024/12/10
 */
@SpringBootApplication
public class WebFluxWebApplication {

    public static void main(String[] args) {
        SpringApplication.run(WebFluxWebApplication.class, args);
    }


    @Bean
    public RouterFunction<ServerResponse> reoutes(OrderHandler handler) {
        return RouterFunctions
                //nest包含两个参数，1，测试条件是否通过，如果通过，则跳转到第二参数指定的路由函数
                .nest(
                        //请求判断路径是否匹配指定的模式（请求路径前缀）
                        RequestPredicates.path("/orders"),
                        //如果匹配通过，则路由到该路由函数
                        //判断请求的报文头字段accept是否匹配APPLICATION_JSON类型
                        RouterFunctions.nest(RequestPredicates.accept(MediaType.APPLICATION_JSON),
                                //如果匹配，则路由到下面的路径函数
                                //将/orders/{id}路由给handler的get方法
                                RouterFunctions.route(RequestPredicates.GET("/{id}"), handler::get)
                                        //如果是get请求/order，则路由到handler的list
                                        .andRoute(RequestPredicates.method(HttpMethod.GET), handler::list)
                                        //如果contentType匹配，并且路径匹配/orders，则路由到函数
                                        .andNest(RequestPredicates.contentType(MediaType.APPLICATION_JSON),
                                                RouterFunctions.route(RequestPredicates.POST("/"), handler::create)
                                        )
                        )

                );
    }


}
