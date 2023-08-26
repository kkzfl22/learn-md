package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/** 追溯的生产者 */
public class TraceProduce {
  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义交换机
      channel.exchangeDeclare("trace.ex", BuiltinExchangeType.DIRECT, false, true, null);
      // 定义队列
      channel.queueDeclare("trace.qu", false, false, true, null);
      // 队列绑定
      channel.queueBind("trace.qu", "trace.ex", "");

      // 定义保留数据队列
      channel.queueDeclare("publishtrace", false, false, false, null);
      // 绑定
      channel.queueBind("publishtrace", "amq.rabbitmq.trace", "publish.trace.ex");

      for (int i = 0; i < 100; i++) {
        String msg = "这是发送的消息:" + i;
        channel.basicPublish("trace.ex", "", null, msg.getBytes(StandardCharsets.UTF_8));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    }
  }
}
