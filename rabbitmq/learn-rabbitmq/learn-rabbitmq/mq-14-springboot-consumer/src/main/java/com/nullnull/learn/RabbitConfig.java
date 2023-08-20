package com.nullnull.learn;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liujun
 * @since 2023/8/20
 */
@Configuration
public class RabbitConfig {

    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("boot.queue").build();
    }


}
