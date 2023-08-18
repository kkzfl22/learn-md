package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

/**
 * 苏州用户的消费者
 *
 * @author liujun
 * @since 2023/8/18
 */
public class SuZhouUserConsumer {

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

    // 定义队列
    channel.queueDeclare(
        "suzhou.user.consumer",
        // 持久化存储
        true,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    // 将队列与交换机进行绑定
    channel.queueBind("suzhou.user.consumer", "ex.busi.topic", "suzhou.user.*", null);

    channel.basicConsume(
        "suzhou.user.consumer",
        (consumerTag, message) -> {
          String dataMsg = new String(message.getBody(), StandardCharsets.UTF_8);
          System.out.println("suzhou consumer 收到数据:" + dataMsg);
        },
        consumerTag -> {});
  }
}
