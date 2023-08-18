package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;

/**
 * 获取所有的错误日志
 *
 * @author liujun
 * @since 2023/8/18
 */
public class ErrorLogConsumer {

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
        "log.all.error",
        // 持久化存储
        true,
        // 排他
        false,
        // 自动删除
        true,
        // 属性
        null);

    // 将队列与交换机进行绑定
    channel.queueBind("log.all.error", "ex.busi.topic", "#.ERROR", null);

    channel.basicConsume(
        "log.all.error",
        (consumerTag, message) -> {
          String dataMsg = new String(message.getBody(), StandardCharsets.UTF_8);
          System.out.println("错误日志 consumer 收到数据:" + dataMsg);
        },
        consumerTag -> {});
  }
}
