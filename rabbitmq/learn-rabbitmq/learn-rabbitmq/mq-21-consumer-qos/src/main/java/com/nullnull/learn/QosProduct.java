package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 日志消息的发送
 *
 * @author liujun
 * @since 2023/8/18
 */
public class QosProduct {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换机
    channel.exchangeDeclare(
        "qos.ex",
        BuiltinExchangeType.DIRECT,
        // 持久化标识
        false,
        // 是否自动删除
        false,
        // 属性信息
        null);

    for (int i = 0; i < 1000; i++) {
      String msg = "这是发送的消息:" + i;
      channel.basicPublish("qos.ex", "qos.rk", null, msg.getBytes(StandardCharsets.UTF_8));
    }
  }
}
