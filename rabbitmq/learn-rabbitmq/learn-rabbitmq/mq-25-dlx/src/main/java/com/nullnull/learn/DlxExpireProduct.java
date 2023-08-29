package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

/**
 * 死信交换器的情况
 *
 * @author liujun
 * @since 2023/8/29
 */
public class DlxExpireProduct {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义测试消息过期的队列和交换器
      channel.exchangeDeclare("dlx.biz.expire.ex", BuiltinExchangeType.FANOUT, false);
      Map<String, Object> argument = new HashMap<>();
      // 消息10秒过期
      argument.put("x-message-ttl", 10000);
      // 当消息过期后，放置于死信队列
      argument.put("x-dead-letter-exchange", "dlx.dead.ex");
      // 设置队列所关联的死信交换器的routingKey，如果没有特殊指定，使用原队列的routingKey
      argument.put("x-dead-letter-routing-key", "rk.dlx.expire");
      channel.queueDeclare("dlx.biz.expire.qu", false, false, false, argument);
      channel.queueBind("dlx.biz.expire.qu", "dlx.biz.expire.ex", "dlx.biz.expire.rk");

      // 定义死信交换器和数据
      channel.exchangeDeclare("dlx.dead.ex", BuiltinExchangeType.DIRECT, true);
      // 用于接收过期后消息的队列
      channel.queueDeclare("dlx.expire.qu", false, false, false, null);
      // 将用于接收过期消息队列与交换器相绑定
      channel.queueBind("dlx.expire.qu", "dlx.dead.ex", "rk.dlx.expire");

      // 测试过期消息的发送
      String msgExpire = "测试过期消息";
      channel.basicPublish(
          "dlx.biz.expire.ex", "", null, msgExpire.getBytes(StandardCharsets.UTF_8));
    } catch (Exception e) {
      e.printStackTrace();
    }
  }
}
