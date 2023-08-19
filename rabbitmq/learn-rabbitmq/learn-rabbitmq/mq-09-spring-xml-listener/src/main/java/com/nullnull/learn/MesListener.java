package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.listener.api.ChannelAwareMessageListener;

/**
 * @author liujun
 * @since 2023/8/19
 */
public class MesListener implements ChannelAwareMessageListener {

    @Override
    public void onMessage(Message message, Channel channel) throws Exception {
        String encoding = message.getMessageProperties().getContentEncoding();
        System.out.println("收到的消息是：" + new String(message.getBody(), encoding));
    }
}
