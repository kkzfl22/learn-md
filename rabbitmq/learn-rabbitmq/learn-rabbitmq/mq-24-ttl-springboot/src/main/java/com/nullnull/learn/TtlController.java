package com.nullnull.learn;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * 测试TTl的控制层
 *
 * @author liujun
 * @since 2023/8/29
 */
@RestController
public class TtlController {

  @Autowired private AmqpTemplate template;

  /**
   * 队列中设置了TTL
   *
   * @return
   */
  @RequestMapping("/queue/ttl")
  public String sendQueueTtl() {

    String msg = "这是发送的测试TTL数据-TTL-WAITING-MESSAGE";

    // 发送
    template.convertAndSend("ttl.wait.ex", "ttl.wait.rk", msg);

    return "ttl-ok";
  }

  @RequestMapping("/message/ttl")
  public String sendMessageTtl() {
    MessageProperties build =
        MessagePropertiesBuilder.newInstance()
            // 过期时间为5秒
            .setExpiration("5000")
            .build();

    String msgStr = "这是发送的数据-消息的TTL";

    Message msg = new Message(msgStr.getBytes(StandardCharsets.UTF_8), build);

    template.convertAndSend("wait.ex", "wait.rk", msg);

    return "msg-ok";
  }
}
