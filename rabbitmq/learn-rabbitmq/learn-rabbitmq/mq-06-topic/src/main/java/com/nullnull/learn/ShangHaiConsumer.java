package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 上海地区的消费都，获取所有的上海信息
 *
 * @author liujun
 * @since 2023/8/18
 */
public class ShangHaiConsumer {

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
        "shanghai.all.log",
        // 持久化存储
        true,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    // 将队列与交换机进行绑定
    channel.queueBind("shanghai.all.log", "ex.busi.topic", "shanghai.#", null);

    channel.basicConsume(
        "shanghai.all.log",
        (consumerTag, message) -> {
          String dataMsg = new String(message.getBody(), StandardCharsets.UTF_8);
          System.out.println("shanghai consumer 收到数据:" + dataMsg);
        },
        consumerTag -> {});
  }
}
