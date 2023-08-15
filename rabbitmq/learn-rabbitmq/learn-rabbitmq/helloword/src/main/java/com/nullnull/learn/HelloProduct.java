package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;

/**
 * 生产者
 *
 * @author liujun
 * @since 2023/8/14
 */
public class HelloProduct {


    public static void main(String[] args) {

        //获取连接工厂
        ConnectionFactory factory = new ConnectionFactory();

        //设置主机名
        factory.setHost("node1");
        //设置虚拟主机名 / 在URL中的转义字符为%2f
        factory.setVirtualHost("/");
        //用户名
        factory.setUsername("root");
        //密码
        factory.setPassword("123456");
        //设置端口
        factory.setPort(5672);


        try (
                //建立TCP连接
                Connection connection = factory.newConnection();
                //获取通道
                Channel channel = connection.createChannel()
        ) {
            //声明一个队列
            //第一个参数为队列名称
            //是否是持久化的
            //是否是排他的
            //是否是自动删除
            //是消除队列的属性，使用默认值
            channel.queueDeclare("queue.biz", false, false, true, null);

            //声明一个交换机
            //交换器的名称
            //交换器的类型
            //交换器是否是持久化
            //交换器是否是自动删除
            //交换器属性map集合
            channel.exchangeDeclare("ex.biz", BuiltinExchangeType.DIRECT, false, false, null);


            //将队列和交换机进行绑定,并指定路由键
            channel.queueBind("queue.biz", "ex.biz", "hello.world");

            //发送消息
            //交换器的名称
            //该消息的路由键
            //该消息的属性MessageProperties对象
            //消息的内容字节数组。
            channel.basicPublish("ex.biz", "hello.world", null, "hello world 1".getBytes());
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
