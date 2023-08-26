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
import java.time.LocalDateTime;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadLocalRandom;

/** 执行QOS的接收端处理 */
public class QosThreadConsumer {
  public static void main(String[] args) throws Exception {

    // 资源限制
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    // 设置channel并发请求最大数
    factory.setRequestedChannelMax(5);

    // 自定义线程池工厂
    ThreadFactory thsFactory = Executors.privilegedThreadFactory();
    factory.setThreadFactory(thsFactory);

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    // 定义交换器、队列和绑定
    channel.exchangeDeclare("qos.ex", BuiltinExchangeType.DIRECT, false, false, null);
    channel.queueDeclare("qos.qu", false, false, false, null);
    channel.queueBind("qos.qu", "qos.ex", "qos.rk");

    // 设置每秒处理2个
    channel.basicQos(5, true);

    channel.basicConsume(
        "qos.qu",
        false,
        new DefaultConsumer(channel) {
          @Override
          public void handleDelivery(
              String consumerTag, Envelope envelope, AMQP.BasicProperties properties, byte[] body)
              throws IOException {

            LocalDateTime time = LocalDateTime.now();
            long threadId = Thread.currentThread().getId();
            System.out.println(
                "[消费]"
                    + time
                    + ",线程:"
                    + threadId
                    + ",收到的消息:"
                    + new String(body, StandardCharsets.UTF_8));

            int randomSleep = ThreadLocalRandom.current().nextInt(1, 200);
            try {
              Thread.sleep(randomSleep);
            } catch (InterruptedException e) {
              e.printStackTrace();
            }

            if (envelope.getDeliveryTag() % 3 == 0) {
              // 进行消息确认
              channel.basicAck(envelope.getDeliveryTag(), true);
            }
          }
        });
  }
}
