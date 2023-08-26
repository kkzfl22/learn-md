package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;
import java.nio.charset.StandardCharsets;

/** 执行QOS的接收端处理 */
public class TraceConsumer {
  public static void main(String[] args) throws Exception {

    // 资源限制
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

      // 定义队列
      channel.queueDeclare("delivertrace", false, false, true, null);
      // 队列绑定
      channel.queueBind("delivertrace", "amq.rabbitmq.trace", "deliver.trace.qu");

      // 接收消息
      for (int i = 0; i < 25; i++) {
        GetResponse getResponse = channel.basicGet("trace.qu", true);
        String msg = new String(getResponse.getBody(), StandardCharsets.UTF_8);
        System.out.println("收到的消息：" + msg);
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
