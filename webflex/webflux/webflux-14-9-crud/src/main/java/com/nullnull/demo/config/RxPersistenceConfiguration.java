package com.nullnull.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import reactor.core.scheduler.Scheduler;
import reactor.core.scheduler.Schedulers;

/**
 * @author nullnull
 * @since 2024/12/20
 */
@Configuration
public class RxPersistenceConfiguration {

    @Bean
    public Scheduler jpaScheduler() {
        return Schedulers.newParallel("JPA", 10);
    }
}
