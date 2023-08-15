package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.CancelCallback;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 消息消费者
 *
 * @author liujun
 * @since 2023/8/15
 */
public class Consumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri("amqp://root:123456@node1:5672/%2f");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        //声明队列,持久化，不自动删除
        channel.queueDeclare("qu.wk", true, false, false, null);


        channel.basicConsume("qu.wk", new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("收到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {
                System.out.println("cancel的消息:" + consumerTag);
            }
        });

    }

}
