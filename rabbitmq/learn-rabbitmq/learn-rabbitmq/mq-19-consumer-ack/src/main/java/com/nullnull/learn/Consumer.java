package com.nullnull.learn;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DefaultConsumer;
import com.rabbitmq.client.Envelope;

import java.io.IOException;

/** ACk消费者 */
public class Consumer {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();

    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    channel.queueDeclare("ack.qu", false, false, false, null);

    DefaultConsumer consumer =
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              // 消费者标签
              String consumerTag,
              // 消费者的封装
              Envelope envelope,
              // 消息属性
              AMQP.BasicProperties properties,
              // 消息体
              byte[] body)
              throws IOException {

            System.out.println("确认的消息内容:" + new String(body));

            //// 消息确认，并且非批量确认，multiple为false，表示只确认了单条
            // channel.basicAck(envelope.getDeliveryTag(), false);

            //
            // 消息不确认，
            // 参数1，消息的标签
            // multiple为false 表示不确认当前是一个消息。true就是多个消息。
            // requeue为true表示不确认的消息会重新放回队列。
            // 每5条做一次批量确认,_deliveryTag从1开始
            if (envelope.getDeliveryTag() % 5 == 0) {
              channel.basicNack(envelope.getDeliveryTag(), true, false);
              System.out.println("批量确认执行");
            }

            // 找收消息
            // Nack与Reject的区别在于，nack可以对多条消息进行拒收，而reject只能拒收一条。
            // requeue为true表示不确认的消息会重新放回队列。
            // channel.basicReject(envelope.getDeliveryTag(), true);
            // channel.basicReject(envelope.getDeliveryTag(), false);
          }
        };

    channel.basicConsume(
        "ack.qu",
        // 非自动确认
        false,
        // 消费者的标签
        "ack.consumer",
        // 回调函数
        consumer);
  }
}
