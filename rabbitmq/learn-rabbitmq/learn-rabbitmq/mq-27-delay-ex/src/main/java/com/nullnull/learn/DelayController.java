package com.nullnull.learn;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * 发送通知测试
 *
 * @author liujun
 * @since 2023/8/30
 */
@RestController
public class DelayController {

  @Autowired private AmqpTemplate template;

  @RequestMapping("/notify/{seconds}")
  public String toMeeting(@PathVariable Integer seconds) throws Exception {

    MessageProperties prop = MessagePropertiesBuilder.newInstance()
            //编码
            .setContentEncoding(StandardCharsets.UTF_8.name())
            //延迟时间
            .setHeader("x-delay", seconds * 1000)
            .build();


    byte[] meetContainer = (seconds + "秒后通知部门会议").getBytes(StandardCharsets.UTF_8);
    Message msg = new Message(meetContainer, prop);

    template.convertAndSend("delay.ex", "delay.rk", msg);

    return "已经设定好了通知";
  }
}
