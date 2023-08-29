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
public class DlxRejectProduct {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义测试消息过期的队列和交换器
    channel.exchangeDeclare("dlx.biz.reject.ex", BuiltinExchangeType.FANOUT, false);
    Map<String, Object> argument = new HashMap<>();
    // 当消息过期后，放置于死信队列
    argument.put("x-dead-letter-exchange", "dlx.dead.ex");
    // 设置队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
    argument.put("x-dead-letter-routing-key", "rk.dlx.reject");
    channel.queueDeclare("dlx.biz.reject.qu", false, false, false, argument);
    channel.queueBind("dlx.biz.reject.qu", "dlx.biz.reject.ex", "dlx.biz.reject.rk");

    // 定义死信交换器和数据
    channel.exchangeDeclare("dlx.dead.ex", BuiltinExchangeType.DIRECT, true);
    // 用于接收过期后消息的队列
    channel.queueDeclare("dlx.reject.qu", false, false, false, null);
    // 将用于接收过期消息队列与交换器相绑定
    channel.queueBind("dlx.reject.qu", "dlx.dead.ex", "rk.dlx.reject");

    channel.basicConsume(
        "dlx.biz.reject.qu",
        false,
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {
            // 进行消息的拒绝,并且不进入队列
            channel.basicReject(envelope.getDeliveryTag(), false);
          }
        });
  }
}
