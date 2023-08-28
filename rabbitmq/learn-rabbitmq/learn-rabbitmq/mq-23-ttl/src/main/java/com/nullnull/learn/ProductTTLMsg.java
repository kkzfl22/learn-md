package com.nullnull.learn;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

public class ProductTTLMsg {
  public static void main(String[] args) throws Exception {

    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {
      // 定义相关的参数
      Map<String, Object> param = new HashMap<>();

      // 设置队列的空闲时间，（如果队列没有消费者或者一直没有使用，队列可存活的时间）
      // 可以理解为没有消费者时，消息队列20秒后删除。
      param.put("x-expires", 20000);

      channel.queueDeclare("ttl.qu", false, false, false, param);

      for (int i = 0; i < 100; i++) {
        String sendMsg = "this is test msg :" + i;

        // 在消息上指定存活时间为8秒
        AMQP.BasicProperties properties =
            new AMQP.BasicProperties().builder().expiration("8000").build();

        channel.basicPublish("", "ttl.qu", properties, sendMsg.getBytes(StandardCharsets.UTF_8));
      }
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
