package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

/**
 * @author liujun
 * @since 2023/8/30
 */
@Component
public class DelayListener {

  @RabbitListener(queues = "delay.qu")
  public void MeetingAlarm(Message msg, Channel channel) throws Exception {
    String alarmMsg = new String(msg.getBody(), msg.getMessageProperties().getContentEncoding());
    System.out.println("收到提醒：" + alarmMsg);
  }
}
