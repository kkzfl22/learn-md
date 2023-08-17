package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.nio.charset.StandardCharsets;

/**
 * 消息的生产者
 *
 * @author liujun
 * @since 2023/8/15
 */
public class Product {


    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        factory.setUri("amqp://root:123456@node1:5672/%2f");

        //创建连接和队列
        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();


        //声明队列,持久化，不自动删除
        channel.queueDeclare("qu.wk", true, false, false, null);


        channel.exchangeDeclare("test","fanout");

        //声明交换机,消息持久化，不自动删除
        channel.exchangeDeclare("ex.wk", BuiltinExchangeType.DIRECT, true, false, null);

        //队列和交换机绑定
        channel.queueBind("qu.wk", "ex.wk", "rk.wq");

        for (int i = 0; i < 20; i++) {
            //发送消息
            channel.basicPublish("ex.wk",
                    "rk.wq", null, ("data msg " + i).getBytes(StandardCharsets.UTF_8));
        }


        channel.close();
        connection.close();
    }

}
