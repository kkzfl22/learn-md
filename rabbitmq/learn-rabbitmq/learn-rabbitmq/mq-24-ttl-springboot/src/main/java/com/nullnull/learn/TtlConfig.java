package com.nullnull.learn;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Controller;

import java.util.HashMap;
import java.util.Map;

/**
 * 队列的配制信息
 *
 * @author liujun
 * @since 2023/8/29
 */
@Configuration
public class TtlConfig {

  /**
   * 在队列上设置等待时间
   *
   * @return
   */
  @Bean
  public Queue queueTtlWaiting() {
    Map<String, Object> props = new HashMap<>();
    // 设置消息队列的等待时间为10S
    props.put("x-message-ttl", 20000);
    // 设置队列的过期时间为20秒，如果没有消费者20秒后，队列被删除
    props.put("x-expires", 10000);
    Queue queue = new Queue("ttl.wait.qu", false, false, false, props);
    return queue;
  }

  /**
   * 定义交换器
   *
   * @return
   */
  @Bean
  public Exchange exchangeTtlWaiting() {
    return new DirectExchange("ttl.wait.ex", false, false);
  }

  /**
   * 队列与交换器绑定
   *
   * @return
   */
  @Bean
  public Binding bindingTtlWaiting() {
    return BindingBuilder.bind(queueTtlWaiting())
        .to(exchangeTtlWaiting())
        .with("ttl.wait.rk")
        .noargs();
  }

  /**
   * 定义一个普通的队列，不设置TTL，给每个消息设置过期时间
   *
   * @return
   */
  @Bean
  public Queue queueWaiting() {
    Map<String, Object> props = new HashMap<>();
    // 设置队列的过期时间为20秒，如果没有消费者20秒后，队列被删除
    props.put("x-expires", 20000);

    Queue queue = new Queue("wait.qu", false, false, false, props);
    return queue;
  }

  /**
   * 定义交换器,给每个消息设置过期时间
   *
   * @return
   */
  @Bean
  public Exchange exchangeWaiting() {
    return new DirectExchange("wait.ex", false, false);
  }

  /**
   * 定义一个队列与交换器的绑定,给每个消息设置过期时间
   *
   * @return
   */
  @Bean
  public Binding bingingWaiting() {
    return BindingBuilder.bind(queueWaiting()).to(exchangeWaiting()).with("wait.rk").noargs();
  }
}
