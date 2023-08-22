package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * @author liujun
 * @since 2023/8/22
 */
public class Product {
  public static void main(String[] args) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义交换器队列
      channel.exchangeDeclare("ack.ex", BuiltinExchangeType.DIRECT, false, false, false, null);
      // 定义队列
      channel.queueDeclare("ack.qu", false, false, false, null);
      // 队列绑定
      channel.queueBind("ack.qu", "ack.ex", "ack.rk");

      for (int i = 0; i < 5; i++) {
        byte[] sendBytes = ("hello-" + i).getBytes(StandardCharsets.UTF_8);
        channel.basicPublish("ack.ex", "ack.rk", null, sendBytes);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
