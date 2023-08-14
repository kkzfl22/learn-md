/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import com.rabbitmq.client.GetResponse;

/**
 * 拉模式的消费者
 *
 * @author liujun
 * @since 2023/8/14
 */
public class HelloGetConsumer {

    public static void main(String[] args) throws Exception {
        ConnectionFactory factory = new ConnectionFactory();

        //指定协议:amqp
        //指定用户名 root
        //指定密码 123456
        //指定host node1
        //指定端口号 5672
        //指定虚拟主机 %2f 即是/
        factory.setUri("amqp://root:123456@node1:5672/%2f");


        try (Connection connection = factory.newConnection();
             Channel channel = connection.createChannel();) {

            //使用拉模式获取数据
            //指定从哪个消费队列中消费消息
            //表示自动ack确认。
            GetResponse getResponse = channel.basicGet("queue.biz", true);

            byte[] body = getResponse.getBody();

            System.out.println("最终的消息:"+new String(body));

        } catch (Exception e) {
            e.printStackTrace();
        }


    }

}
