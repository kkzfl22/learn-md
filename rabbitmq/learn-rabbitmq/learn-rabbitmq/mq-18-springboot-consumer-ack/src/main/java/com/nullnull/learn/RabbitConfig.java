package com.nullnull.learn;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author liujun
 * @since 2023/8/22
 */
@Configuration
public class RabbitConfig {

  @Bean
  public Queue queue() {
    return new Queue("ack.qu", false, false, false, null);
  }

  @Bean
  public Exchange exchange()
  {
    return new DirectExchange("ack.ex",false,false,null);
  }


  @Bean
  public Binding binding()
  {
    return BindingBuilder.bind(queue()).to(exchange()).with("ack.rk").noargs();
  }
}
