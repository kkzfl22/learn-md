package com.nullnull.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liujun
 * @since 2023/8/19
 */
public class Product {

    public static void main(String[] args) throws Exception {
        AbstractApplicationContext context = new ClassPathXmlApplicationContext("spring-rabbit.xml");


        RabbitTemplate template = context.getBean(RabbitTemplate.class);


        MessagePropertiesBuilder propertiesBuilder = MessagePropertiesBuilder.newInstance();
        propertiesBuilder.setContentEncoding("gbk");
        propertiesBuilder.setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN);


        Message msg = MessageBuilder.withBody("hello world".getBytes("gbk"))
                .andProperties(propertiesBuilder.build()).build();

        template.convertAndSend("ex.direct", "routing.q1", msg);


        context.close();
    }
}
