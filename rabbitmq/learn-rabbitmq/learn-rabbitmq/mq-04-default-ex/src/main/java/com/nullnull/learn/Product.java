package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 生产者
 *
 * @author liujun
 * @since 2023/8/18
 */
public class Product {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare(
        "queue.default.ex",
        // 是否活动MQ重启
        false,
        // 是否只能自己这个连接来使用
        false,
        // 是否自动删除
        false,
        // 参数
        null);

    try {
      //在发送消息的时候没有指定交换器的名称，此时使用的是默认的交换器，默认交换器没有名称。
      //路由键就是目的消息队列的名称
      channel.basicPublish(
          // 空字符使用默认交换机
          "",
          // 指定队列
          "queue.default.ex",
          // 参数
          null,
          // 发送的消息
          "hello nullnull".getBytes(StandardCharsets.UTF_8));
    } catch (IOException e) {
      e.printStackTrace();
    } finally {
      channel.close();
      connection.close();
    }
  }
}
