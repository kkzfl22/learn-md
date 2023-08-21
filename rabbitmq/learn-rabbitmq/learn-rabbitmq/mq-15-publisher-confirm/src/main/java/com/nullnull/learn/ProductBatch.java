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
public class ProductBatch {

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

    // 批量处理大小，即每10条进行一次确认
    int batch = 10;
    // 确认的计数
    int confirmNum = 0;

    // 执行发送端确认机制
    try {
      for (int i = 0; i < 108; i++) {
        confirmNum++;
        String pushMsg = "confirm 这是推送确认的消息" + i;
        channel.basicPublish(
            "confirm.ex", "confirm.rk", null, pushMsg.getBytes(StandardCharsets.UTF_8));

        if (confirmNum == batch) {
          channel.waitForConfirmsOrDie(5000);
          System.out.println("批量发送的消息被确认:");
          confirmNum = 0;
        }
      }

      if (confirmNum > 0) {
        channel.waitForConfirmsOrDie(5000);
        System.out.println("剩余发送的消息被确认:");
      }
    } catch (IOException e) {
      e.printStackTrace();
      System.out.println("消息被拒绝:");
    } catch (InterruptedException e) {
      e.printStackTrace();
      System.out.println("非Publisher confirm的通道上使用该方法");
    } catch (TimeoutException e) {
      e.printStackTrace();
      System.out.println("等待消息确认超时");
    }
  }
}
