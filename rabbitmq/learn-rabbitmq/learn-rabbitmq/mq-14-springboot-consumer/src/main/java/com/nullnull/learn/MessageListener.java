package com.nullnull.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.nio.charset.StandardCharsets;

/**
 * 消息的监听器
 *
 * @author liujun
 * @since 2023/8/20
 */
@Component
public class MessageListener {


    @RabbitListener(queues = "boot.queue")
    public void getMsg(Message msg) {
        String headValue = msg.getMessageProperties().getHeader("key");
        String getValue = new String(msg.getBody(), StandardCharsets.UTF_8);
        System.out.println("接收到的消息头" + headValue);
        System.out.println("接收到的消息:" + getValue);
    }


    //@RabbitListener(queues = "boot.queue")
    //public void getMsgPay(@Payload String msg, @Header("key") String keyValue) {
    //    System.out.println("接收到的消息头:" + keyValue);
    //    System.out.println("接收到的消息:" + msg);
    //}

}
