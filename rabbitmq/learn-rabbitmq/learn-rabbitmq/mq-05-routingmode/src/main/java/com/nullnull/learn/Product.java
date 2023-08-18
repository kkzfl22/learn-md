package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 路由模式的生产者
 *
 * @author liujun
 * @since 2023/8/18
 */
public class Product {

  private static final String[] LOG_LEVEL = {"ERROR", "WARN", "INFO"};

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 声明交换机，交换器和消息队列的绑定不需要在这里处理。
    channel.exchangeDeclare(
        "ex.routing",
        BuiltinExchangeType.DIRECT,
        // 持久的标识
        false,
        // 自动删除的标识
        false,
        // 属性
        null);

    for (int i = 0; i < 30; i++) {
      String level = LOG_LEVEL[ThreadLocalRandom.current().nextInt(0, LOG_LEVEL.length)];
      String dataMsg = "[" + level + "] 消息发送 :" + i;
      // 发送消息
      channel.basicPublish("ex.routing", level, null, dataMsg.getBytes(StandardCharsets.UTF_8));
    }
  }
}
