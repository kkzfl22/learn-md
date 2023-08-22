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

      // 交换器元数据持久化 durable参数为true，表示要持久化
      channel.exchangeDeclare(
          "persistent.ex", BuiltinExchangeType.DIRECT, true, false, false, null);
      // 队列元数据持久化，durable参数为true，表示要持久化
      channel.queueDeclare("persistent.qu", true, false, false, null);

      // 交换机与队列绑定
      channel.queueBind("persistent.qu", "persistent.ex", "persistent.rk");

      String msg = "hello world:" ;
      AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
      builder.contentType("text/plain");
      // 发送的消息持久化
      builder.deliveryMode(2);
      AMQP.BasicProperties properties = builder.build();
      channel.basicPublish(
          "persistent.ex", "persistent.rk", properties, msg.getBytes(StandardCharsets.UTF_8));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
