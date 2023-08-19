package com.nullnull.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 消费者-使用拉消息的模式
 *
 * @author liujun
 * @since 2023/8/19
 */
public class ConsumerGet {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-rabbit.xml");

        RabbitTemplate template = context.getBean(RabbitTemplate.class);


        Message receive = template.receive("queue.msg");

        //报文头中的消息信息
        String encoding = receive.getMessageProperties().getContentEncoding();

        System.out.println("收到的消息:" + new String(receive.getBody(), encoding));


        context.close();
    }
}
