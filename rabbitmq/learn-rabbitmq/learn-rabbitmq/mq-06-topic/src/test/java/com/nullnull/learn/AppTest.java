package com.nullnull.learn;

import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * Unit test for simple App.
 */
public class AppTest
        extends TestCase {


    /**
     * Rigourous Test :-)
     */
    public void testApp() throws Exception {

        String msg = "";

        ConnectionFactory factory = new ConnectionFactory();
        factory.setUri("amqp://root:123456@node1:5672/%2f");

        Connection connection = factory.newConnection();
        Channel channel = connection.createChannel();

        try {
            //将channel设置为事务模式
            channel.txSelect();

            //发布消息到交换器、路由Key为空
            channel.basicPublish("ex.push", "", null, msg.getBytes(StandardCharsets.UTF_8));

            //提交事务、只有消息成功被Broker接收了才能提交成功
            channel.txCommit();
        } catch (IOException e) {
            //事务回滚
            channel.txRollback();
        }

    }
}
