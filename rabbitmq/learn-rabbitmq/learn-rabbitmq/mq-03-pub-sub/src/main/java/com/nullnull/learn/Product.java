package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * 生产者
 *
 * @author liujun
 * @since 2023/8/17
 */
public class Product {


    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@node1:5672/%2f");


        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        try {
            //声明fanout类型交换机
            channel.exchangeDeclare("ex.testfan", "fanout", true, false, false, null);

            for (int i = 0; i < 20; i++) {
                channel.basicPublish("ex.testfan",
                        //路由key
                        "",
                        //属性
                        null,
                        //信息
                        ("hello world fan " + i).getBytes(StandardCharsets.UTF_8));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            channel.close();
            connection.close();
        }


    }


}
