package com.nullnull.learn;

import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.connection.CorrelationData;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author liujun
 * @since 2023/8/21
 */
@RestController
public class DataController {

  private RabbitTemplate template;

  /** 用于记录下发送的容器 */
  private Map<Long, String> ackMap = new ConcurrentHashMap<>();

  @Autowired
  public void setTemplate(RabbitTemplate template) {
    this.template = template;

    // 设置回调函数
    RabbitTemplate.ConfirmCallback ackCallback =
        new RabbitTemplate.ConfirmCallback() {
          @Override
          public void confirm(CorrelationData correlationData, boolean ack, String cause) {
            if (ack) {
              String msg =
                  new String(
                      correlationData.getReturnedMessage().getBody(), StandardCharsets.UTF_8);
              ackMap.remove(Long.parseLong(correlationData.getId()));
              System.out.println("【回调】消息确认：" + correlationData.getId() + "--" + msg);
            } else {
              System.out.println("异常：" + cause);
            }
          }
        };

    this.template.setConfirmCallback(ackCallback);
  }

  @RequestMapping("/biz")
  public String doInvoke() throws Exception {

    long sendSeq = 1;

    for (int i = 0; i < 28; i++) {
      MessageProperties build =
          MessagePropertiesBuilder.newInstance()
              .setHeader("key1", "value1")
              .setCorrelationId(sendSeq + "")
              .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
              .build();
      build.setConsumerTag("msg");
      CorrelationData dataSend = new CorrelationData();
      dataSend.setId(sendSeq + "");

      // 用于回调验证的消息
      byte[] returnBytes = "这是响应的消息:".getBytes(StandardCharsets.UTF_8);
      dataSend.setReturnedMessage(new Message(returnBytes, null));

      // 发送的消息
      String msg = "这是等待确认的消息";
      byte[] sendBytes = msg.getBytes(StandardCharsets.UTF_8);
      Message message = new Message(sendBytes, build);
      template.convertAndSend("confirm.ex", "confirm.rk", message, dataSend);

      ackMap.put(sendSeq, msg);

      System.out.println("【发送】发送成功:" + sendSeq);
      Thread.sleep(ThreadLocalRandom.current().nextInt(0, 10));

      sendSeq = sendSeq + 1;
    }

    Thread.sleep(3000);
    System.out.println("未确认ACK的消息:" + ackMap.size());

    return "ok";
  }

}
