package com.nullnull.learn;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeBuilder;
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

    @Bean
    public Exchange exchange() {
        Exchange exchange = ExchangeBuilder.directExchange("boot.ex").build();
        return exchange;
    }

    @Bean
    public Binding binding() {
        return new Binding("boot.queue", Binding.DestinationType.QUEUE, "boot.ex", "boot.rk", null);
    }

}
