package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 消费者
 *
 * @author liujun
 * @since 2023/8/18
 */
public class ErrorConsumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明队列并绑定
    channel.exchangeDeclare(
        "ex.routing",
        BuiltinExchangeType.DIRECT,
        // 持久的标识
        false,
        // 自动删除的标识
        false,
        // 属性
        null);

    // 此也可以声明为临时队列，但是如果消息很重要，不要声明临时队列。
    channel.queueDeclare(
        "log.error",
        // 永久
        false,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    // 消费者享有绑定到交换器的权力。
    channel.queueBind("log.error", "ex.routing", "ERROR");

    // 通过chanel消费消息
    channel.basicConsume(
        "log.error",
        (consumerTag, message) -> {
          System.out.println("ERROR收到的消息:" + new String(message.getBody(), StandardCharsets.UTF_8));
        },
        consumerTag -> {});
  }
}
