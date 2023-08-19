package com.nullnull.learn;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Exchange;
import org.springframework.amqp.core.FanoutExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.QueueBuilder;
import org.springframework.amqp.rabbit.connection.CachingConnectionFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitAdmin;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Bean;

import java.net.URI;

/**
 * @author liujun
 * @since 2023/8/19
 */
@Configurable
public class RabbitConfig {

    /**
     * 连接工厂
     *
     * @return
     */
    @Bean
    public ConnectionFactory getConnectionFactory() {
        URI uri = URI.create("amqp://root:123456@node1:5672/%2f");
        ConnectionFactory factory = new CachingConnectionFactory(uri);
        return factory;
    }

    /**
     * RabbitTemplate
     */
    @Bean
    @Autowired
    public RabbitTemplate rabbitTemplate(ConnectionFactory factory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(factory);
        return rabbitTemplate;
    }


    /**
     * RabbitAdmin
     */
    @Bean
    @Autowired
    public RabbitAdmin rabbitAdmin(ConnectionFactory factory) {
        RabbitAdmin admin = new RabbitAdmin(factory);
        return admin;
    }

    /**
     * Queue
     */
    @Bean
    public Queue queue() {
        Queue queue = QueueBuilder.nonDurable("queue.anno")
                //是否排外，即是否只有当前这个连接才能看到。
                //.exclusive()
                //是否自动删除
                //.autoDelete()
                .build();

        return queue;
    }


    /**
     * Exchange
     */
    @Bean
    public Exchange exchange() {
        Exchange exchange = new FanoutExchange("ex.anno.fanout", false, false, null);
        return exchange;
    }


    /**
     * Binding
     */
    @Bean
    @Autowired
    public Binding binding(Queue queue, Exchange exchange) {
        //创建一个不指定参数的绑定
        Binding binding = BindingBuilder.bind(queue).to(exchange).with("routing.anno").noargs();
        return binding;
    }

}
