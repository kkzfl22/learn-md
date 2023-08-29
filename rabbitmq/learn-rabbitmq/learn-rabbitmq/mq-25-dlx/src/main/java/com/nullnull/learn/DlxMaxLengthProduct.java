package com.nullnull.learn;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 消息被拒绝的情况
 *
 * @author liujun
 * @since 2023/8/29
 */
public class DlxMaxLengthProduct {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义测试消息过期的队列和交换器
      channel.exchangeDeclare("dlx.biz.max.length.ex", BuiltinExchangeType.FANOUT, false);
      Map<String, Object> argument = new HashMap<>();
      // 当消息过期后，放置于死信队列
      argument.put("x-dead-letter-exchange", "dlx.dead.ex");
      // 设置队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
      argument.put("x-dead-letter-routing-key", "rk.dlx.max.length");
      // 定义消息的最大长度为2，超过2个，第三个即为死信消息
      argument.put("x-max-length", 2);
      channel.queueDeclare("dlx.biz.max.length.qu", false, false, false, argument);
      channel.queueBind("dlx.biz.max.length.qu", "dlx.biz.max.length.ex", "dlx.biz.max.length.rk");

      // 定义死信交换器和数据
      channel.exchangeDeclare("dlx.dead.ex", BuiltinExchangeType.DIRECT, true);
      // 用于接收过期后消息的队列
      channel.queueDeclare("dlx.max.length.qu", false, false, false, null);
      // 将用于接收过期消息队列与交换器相绑定
      channel.queueBind("dlx.max.length.qu", "dlx.dead.ex", "rk.dlx.max.length");


      String push1 = "测试发送消息1";
      String push2 = "测试发送消息2";
      String push3 = "测试发送消息3";
      channel.basicPublish("dlx.biz.max.length.ex","",null,push1.getBytes(StandardCharsets.UTF_8));
      channel.basicPublish("dlx.biz.max.length.ex","",null,push2.getBytes(StandardCharsets.UTF_8));
      channel.basicPublish("dlx.biz.max.length.ex","",null,push3.getBytes(StandardCharsets.UTF_8));

    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
