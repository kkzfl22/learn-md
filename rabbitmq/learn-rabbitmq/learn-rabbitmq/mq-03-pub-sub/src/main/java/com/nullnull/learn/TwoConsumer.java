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
 * @author liujun
 * @since 2023/8/17
 */
public class TwoConsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri("amqp://root:123456@node1:5672/%2f");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        //生成的临时队列
        String queueName = channel.queueDeclare().getQueue();
        System.out.println("昨时队列的名称：" + queueName);

        //定义交换机
        channel.exchangeDeclare("ex.testfan",
                BuiltinExchangeType.FANOUT,
                true,
                false,
                null);

        //消息队列与交换机的绑定
        channel.queueBind(queueName, "ex.testfan", "");


        channel.basicConsume(queueName, new DeliverCallback() {
            @Override
            public void handle(String consumerTag, Delivery message) throws IOException {
                System.out.println("two 获取到的消息：" + new String(message.getBody(), StandardCharsets.UTF_8));
            }
        }, new CancelCallback() {
            @Override
            public void handle(String consumerTag) throws IOException {

            }
        });

    }

}
