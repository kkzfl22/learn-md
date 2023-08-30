package com.nullnull.learn;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.CustomExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.ExchangeTypes;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * @author liujun
 * @since 2023/8/30
 */
@Configuration
@RabbitListener
@ComponentScan("com.nullnull.learn")
public class DelayConfig {

  @Bean
  public Queue queue() {
    Queue queue = new Queue("delay.qu", false, false, true, null);
    return queue;
  }

  @Bean
  public Exchange exchange() {
    Map<String, Object> argument = new HashMap<>();
    argument.put("x-delayed-type", ExchangeTypes.FANOUT);
    Exchange exchange = new CustomExchange("delay.ex", "x-delayed-message", true, false, argument);
    return exchange;
  }

  @Bean
  public Binding bind() {
    return BindingBuilder.bind(queue()).to(exchange()).with("delay.rk").noargs();
  }
}
