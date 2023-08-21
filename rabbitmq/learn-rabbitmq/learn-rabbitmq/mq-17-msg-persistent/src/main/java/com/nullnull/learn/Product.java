package com.nullnull.learn;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

/**
 * @author liujun
 * @since 2023/8/21
 */
public class Product {

  public static void main(String[] args) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 交换器元数据持久化
      channel.exchangeDeclare("persistent.ex", BuiltinExchangeType.DIRECT, true);

      // 队列元数据持久化
      channel.queueDeclare("persistent.qu", true, false, false, null);

      for (int i = 0; i < 100; i++) {
        String msg = "这是发送的持久化消息:" + i;
        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.contentType("text/plain");
        // 发送的消息持久化
        builder.deliveryMode(2);
        AMQP.BasicProperties properties = builder.build();
        channel.basicPublish(
            "persistent.ex", "persistent.rk", properties, msg.getBytes(StandardCharsets.UTF_8));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
