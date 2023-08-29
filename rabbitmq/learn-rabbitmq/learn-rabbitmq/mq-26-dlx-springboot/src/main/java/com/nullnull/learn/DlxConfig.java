package com.nullnull.learn;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列的配制操作
 *
 * @author liujun
 * @since 2023/8/29
 */
@Configuration
public class DlxConfig {

  /**
   * 业务队列，并指定了死信交换器
   *
   * @return
   */
  @Bean
  public Queue bizQueue() {
    Map<String, Object> argument = new HashMap<>();

    // 消息在10秒后过期
    argument.put("x-message-ttl", 5000);
    // 设置该队列所关联的死信交换器，当消息超过10秒没有消费，则加入死信队列
    argument.put("x-dead-letter-exchange", "dlx.springboot.ex");
    // 设置该队列所关联的死信交换器的routingKey,如果没有特殊的指定，使用原队列的routingKey.
    argument.put("x-dead-letter-routing-key", "dlx.springboot.rk");
    Queue queue = new Queue("dlx.spring.biz.qu", false, false, false, argument);
    return queue;
  }

  /**
   * 业务交换器
   *
   * @return
   */
  @Bean
  public Exchange bizExchange() {
    return new DirectExchange("dlx.spring.biz.ex", false, false, null);
  }

  @Bean
  public Binding bizBind() {
    return BindingBuilder.bind(bizQueue()).to(bizExchange()).with("dlx.spring.biz.rk").noargs();
  }

  /**
   * 死信队列
   *
   * @return
   */
  @Bean
  public Queue queueDlx() {
    return new Queue("dlx.springboot.expire.qu", false, false, false);
  }

  @Bean
  public Exchange exchangeDlx() {
    return new DirectExchange("dlx.springboot.ex", true, false, null);
  }

  @Bean
  public Binding bindDlx() {
    return BindingBuilder.bind(queueDlx()).to(exchangeDlx()).with("dlx.springboot.rk").noargs();
  }
}
