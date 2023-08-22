package com.nullnull.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import java.nio.charset.StandardCharsets;

/** */
@SpringBootApplication
public class Main {

  @Autowired private RabbitTemplate rabbitTemplate;

  public static void main(String[] args) {
    SpringApplication.run(Main.class, args);
  }

  /**
   * 在启动后就开始向MQ中发送消息
   *
   * @return
   */
  @Bean
  public ApplicationRunner runner() {
    return args -> {
      Thread.sleep(5000);
      for (int i = 0; i < 10; i++) {
        MessageProperties props = new MessageProperties();
        props.setDeliveryTag(i);
        Message message = new Message(("消息:" + i).getBytes(StandardCharsets.UTF_8), props);
        rabbitTemplate.convertAndSend("ack.ex", "ack.rk", message);
      }
    };
  }
}
