package com.nullnull.learn;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.TimeoutException;

/**
 * 消息的生产者
 *
 * @author liujun
 * @since 2023/8/21
 */
public class Product {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 开启发送方确认机制
    AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

    channel.exchangeDeclare("confirm.ex", BuiltinExchangeType.DIRECT);
    channel.queueDeclare("confirm.qe", false, false, false, null);
    channel.queueBind("confirm.qe", "confirm.ex", "confirm.rk");

    String pushMsg = "confirm 这是推送确认的消息";

    channel.basicPublish(
        "confirm.ex", "confirm.rk", null, pushMsg.getBytes(StandardCharsets.UTF_8));

    // 执行发送端确认机制
    try {
      channel.waitForConfirmsOrDie(5000);
      System.out.println("发送的消息被确认:" + pushMsg);
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("消息被拒绝:" + pushMsg);
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.out.println("非Publisher confirm的通道上使用该方法");
    } catch (TimeoutException e) {
      e.printStackTrace();
      System.out.println("等待消息确认超时");
    }
  }
}
