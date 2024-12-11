package com.nullnull.demo.client;

import org.junit.jupiter.api.Test;
//import org.junit.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.test.StepVerifier;

import java.time.Duration;

/**
 * 应用程序的运行
 *
 * @author nullnull
 * @since 2024/12/11
 */
public class TestStandaloneService {


    @Test
    public void checkApplicationRunning() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        DefaultPasswordVerificationService server = new DefaultPasswordVerificationService(WebClient.builder());
        StepVerifier.create(server.check("test", encoder.encode("test")))
                .expectSubscription()
                .expectComplete()
                .verify(Duration.ofSeconds(20));
    }


    @Test
    public void checkApplicationRunningError() {
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder(5);
        DefaultPasswordVerificationService server = new DefaultPasswordVerificationService(WebClient.builder());
        StepVerifier.create(server.check("21test1", encoder.encode("dd")))
                .expectSubscription()
                .expectComplete()
                .verify(Duration.ofSeconds(20));
    }

}
