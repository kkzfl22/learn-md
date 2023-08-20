package com.nullnull.learn;


import org.springframework.amqp.core.AcknowledgeMode;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.amqp.core.Queue;

import java.net.URI;

/**
 * rabbit的配制
 *
 * @author liujun
 * @since 2023/8/20
 */
@EnableRabbit
//@ComponentScan("com.nullnull.learn")
@ComponentScan
@Configurable //xml中也可以使用<rabbit:annotation-driven/> 启用@RabbitListener注解
public class RabbitConfig {


    @Bean
    public ConnectionFactory connectionFactory() {
        URI uriInfo = URI.create("amqp://root:123456@node1:5672/%2f");
        return new CachingConnectionFactory(uriInfo);
    }


    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        return new RabbitAdmin(factory);
    }

    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        return new RabbitTemplate(factory);
    }


    @Bean
    public Queue queue() {
        return QueueBuilder.nonDurable("queue.anno").build();
    }


    /**
     * RabbitListener的容器管理对象
     * <p>
     * 使用监听器监听推送过来的消息。在一个应用中可能会有多个监听器。这些监听器是需要一个工厂管理起来的。
     *
     * @return
     */
    @Bean("rabbitListenerContainerFactory")
    @Autowired
    public SimpleRabbitListenerContainerFactory containerFactory(ConnectionFactory connectFactory) {
        SimpleRabbitListenerContainerFactory containerFactory = new SimpleRabbitListenerContainerFactory();

        //要管理容器就得有连接
        containerFactory.setConnectionFactory(connectFactory);
        containerFactory.setAcknowledgeMode(AcknowledgeMode.AUTO);
        //containerFactory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        //containerFactory.setAcknowledgeMode(AcknowledgeMode.NONE);
        //设置并发的消费者，即可以同时存在10个消费都消费消息。
        containerFactory.setConcurrentConsumers(10);
        //设置并发的最大消费者。
        containerFactory.setMaxConcurrentConsumers(15);
        //按照批次处理消息消息。
        containerFactory.setBatchSize(10);
        return containerFactory;
    }


}
