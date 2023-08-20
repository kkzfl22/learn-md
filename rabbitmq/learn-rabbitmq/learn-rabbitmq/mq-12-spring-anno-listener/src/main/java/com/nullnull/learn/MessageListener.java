package com.nullnull.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * 使用注解的监听式处理
 *
 * @author liujun
 * @since 2023/8/20
 */
@Component
public class MessageListener {


    /**
     * com.rabbitmq.client.Channel to get access to the Channel channel对象
     * org.springframework.amqp.core.Message  message对象，可以直接操作原生的AMQP消息
     * org.springframework.messaging.Message to use the messaging abstraction counterpart
     *
     * @Payload-annotated 注解方法参数，该参数的值就是消息体。   method arguments including the support of validation
     * @Header-annotated 注解方法参数，访问指定的消息头字段的值。 method arguments to extract a specific header value, including standard AMQP headers defined by AmqpHeaders
     * @Headers-annotated 该注解的参数获取该消息的消息头的所有字段，参数集合类型对应的MAP argument that must also be assignable to java.util.Map for getting access to all headers.
     * MessageHeaders 参数类型，访问所有消息头字段  arguments for getting access to all headers.
     * MessageHeaderAccessor or AmqpMessageHeaderAccessor  访问所有消息头字段。
     * <p>
     * 消息监听
     */
    @RabbitListener(queues = "queue.anno")
    public void whenMessageCome(Message msg) throws Exception {
        String encoding = msg.getMessageProperties().getContentEncoding();
        System.out.println("收到的消息:" + new String(msg.getBody(), encoding));
    }


    /**
    // * 使用payload进行消费
    // *
    // * 不可同时存在相同的队列被两个监听
    // *
    // * @param data
    // */
    //@RabbitListener(queues = "queue.anno")
    //public void whenMessageConsumer(@Payload String data) {
    //    System.out.println("收到的消息:" + data);
    //}

}
