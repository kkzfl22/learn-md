package com.maxwell.datack;

import com.maxwell.datack.dto.PasswordDTO;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.HttpHandler;
import org.springframework.http.server.reactive.ReactorHttpHandlerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunction;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerResponse;
import reactor.netty.DisposableServer;
import reactor.netty.http.server.HttpServer;

/**
 * @author nullnull
 * @since 2024/12/10
 */
public class StandaloneApplication {

    private static final Logger LOGGER = LoggerFactory.getLogger(StandaloneApplication.class);

    public static void main(String[] args) {
        long start = System.currentTimeMillis();
        //调用routes方法，然后将routerFunction转换为HttpHandler.
        HttpHandler httpHandler = RouterFunctions.toHttpHandler(
                //BCtypt算法进行18轮散列，这可能需要几秒种来进行编码/匹配。
                routes(
                        new BCryptPasswordEncoder(18)
                ));
        //内置HttpHandler适配器
        ReactorHttpHandlerAdapter reactorHttpHandlerAdapter = new ReactorHttpHandlerAdapter(httpHandler);
        //创建HttpServer实例，它是ReactorNettyAPI的一部分
        DisposableServer server = HttpServer.create()
                .host("127.0.0.1")
                .port(8080)
                .handle(reactorHttpHandlerAdapter)
                .bindNow();

        LOGGER.debug("Started is " + (System.currentTimeMillis() - start) + "ms");
        server.onDispose().block();


    }


    private static RouterFunction<ServerResponse> routes(PasswordEncoder passwordEncoder) {
        return RouterFunctions.route(RequestPredicates.POST("/password"),
                serverRequest ->
                        serverRequest
                                .bodyToMono(PasswordDTO.class)
                                //使用passwordEncoder检查已加密密码的原始密码
                                .map(p -> passwordEncoder.matches(p.getRaw(), p.getSecured()))
                                //如果密码与存储的密码匹配，则serverResponse状态将返回ok状态
                                //否则返回EXPECTATION_FAILED(417)
                                .flatMap(isMatched -> isMatched
                                        ? ServerResponse.ok().build()
                                        : ServerResponse.status(HttpStatus.EXPECTATION_FAILED).build()
                                )
        );
    }


}
