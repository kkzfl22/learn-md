/*
 * Copyright (C), 2008-2021, Paraview All Rights Reserved.
 */
package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

import java.net.URISyntaxException;
import java.security.KeyManagementException;
import java.security.NoSuchAlgorithmException;

/**
 * 消费者使用推模式获取消息
 *
 * @author liujun
 * @since 2023/8/14
 */
public class HelloConsumeConsumer {


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


            //由于消息在完成后会删除队列，所以，需要保证消息队列存在
            //声明一个队列
            //第一个参数为队列名称
            //是否是持久化的
            //是否是排他的
            //是否是自动删除
            //是消除队列的属性，使用默认值
            channel.queueDeclare("queue.biz", false, false, true, null);


            //监听消息，一旦有消息推送过来，就调用第一个lambda表达式。
            channel.basicConsume("queue.biz", (consumerTag, message) -> {
                System.out.println("获取的数据:" + new String(message.getBody()));
            }, (consumerTag -> {
            }));
        } catch (Exception e) {
            e.printStackTrace();
        }


    }
}
