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
public class Product {

  private static final String[] ADDRESS_ARRAYS = {"shanghai", "suzhou", "hangzhou"};

  private static final String[] BUSI_NAMES = {"product", "user", "schedule"};

  private static final String[] LOG_LEVEL = {"ERROR", "WARN", "INFO"};

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换机
    channel.exchangeDeclare(
        "ex.busi.topic",
        BuiltinExchangeType.TOPIC,
        // 持久化标识
        false,
        // 是否自动删除
        false,
        // 属性信息
        null);

    for (int i = 0; i < 50; i++) {

      String level = LOG_LEVEL[ThreadLocalRandom.current().nextInt(0, LOG_LEVEL.length)];
      String busiName = BUSI_NAMES[ThreadLocalRandom.current().nextInt(0, BUSI_NAMES.length)];
      String address =
          ADDRESS_ARRAYS[ThreadLocalRandom.current().nextInt(0, ADDRESS_ARRAYS.length)];
      String routingKey = address + "." + busiName + "." + level;

      String pushMsg = "地址[" + address + "],业务[" + busiName + "],级别[" + level + "],消息:" + i;

      channel.basicPublish(
          "ex.busi.topic", routingKey, null, pushMsg.getBytes(StandardCharsets.UTF_8));
    }
  }
}
