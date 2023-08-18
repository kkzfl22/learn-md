package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

import java.io.IOException;

/**
 * 消费消息
 *
 * @author liujun
 * @since 2023/8/18
 */
public class Consumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    try {
      GetResponse getResponse = channel.basicGet("queue.default.ex", true);

      System.out.println("信息输出:" + new String(getResponse.getBody()));
    } catch (IOException e) {
      throw new RuntimeException(e);
    } finally {
      channel.close();
      connection.close();
    }
  }
}
