package com.nullnull.learn;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * rabbitMQ的配制类
 *
 * @author liujun
 * @since 2023/8/21
 */
@Configuration
public class RabbitConfig {

  @Bean
  public Queue queue() {
    return new Queue("confirm.qe", false, false, false, null);
  }

  @Bean
  public Exchange exchange() {
    return new DirectExchange("confirm.ex", false, false, null);
  }

  @Bean
  public Binding binding() {
    return BindingBuilder.bind(queue()).to(exchange()).with("confirm.rk").noargs();
  }
}
