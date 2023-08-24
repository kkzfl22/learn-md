package com.nullnull.learn;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.MessageProperties;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

/** 资源限制 */
public class ResourceLimitProduct {
  public static void main(String[] args) throws Exception {
    // 资源限制
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    try (Connection connection = factory.newConnection();
        Channel channel = connection.createChannel(); ) {

      // 定义交换器、队列和绑定
      channel.exchangeDeclare("res.limit.ex", BuiltinExchangeType.DIRECT, true, false, null);
      channel.queueDeclare("res.limit.qu", true, false, false, null);
      channel.queueBind("res.limit.qu", "res.limit.ex", "res.limit.rk");

      // 开启发送方确认机制
      AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

      ConfirmCallback confirm =
          new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
              if (multiple) {
                System.out.println("【批量确认】:小于" + deliveryTag + "已经确认");
              } else {
                System.out.println("【单条确认】:等于" + deliveryTag + "已经确认");
              }
            }
          };

      ConfirmCallback nackConfirm =
          new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
              if (multiple) {
                System.out.println("【批量不确认】:小于" + deliveryTag + "已经确认");
              } else {
                System.out.println("【单条不确认】:等于" + deliveryTag + "已经确认");
              }
            }
          };

      channel.addConfirmListener(confirm, nackConfirm);

      for (int i = 0; i < 100000000; i++) {
        String msg = getKbMessage(i);
        long sequence = channel.getNextPublishSeqNo();
        System.out.println("【发送】成功了序列消息:" + sequence);

        AMQP.BasicProperties.Builder builder = new AMQP.BasicProperties.Builder();
        builder.contentType("text/plain");
        // 发送的消息持久化
        builder.deliveryMode(2);
        AMQP.BasicProperties properties = builder.build();

        channel.basicPublish(
            "res.limit.ex", "res.limit.rk", properties, msg.getBytes(StandardCharsets.UTF_8));

        Thread.sleep(ThreadLocalRandom.current().nextInt(5, 100));
      }
    } catch (IOException e) {
      e.printStackTrace();
    } catch (TimeoutException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    }
  }

  private static String getKbMessage(int i) {
    StringBuilder msg = new StringBuilder("发送确认消息:" + i + "--");

    for (int j = 0; j < 102400; j++) {
      msg.append(j);
    }

    return msg.toString();
  }
}
