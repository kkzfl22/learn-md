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

/**
 * @author liujun
 * @since 2023/8/21
 */
@RestController
public class DataController {

  private RabbitTemplate template;

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
              System.out.println("回调消息确认：" + correlationData.getId() + "--" + msg);
            } else {
              System.out.println(cause);
            }
          }
        };

    this.template.setConfirmCallback(ackCallback);
  }

  @RequestMapping("/biz")
  public String doInvoke() {
    MessageProperties build =
        MessagePropertiesBuilder.newInstance()
            .setHeader("key1", "value1")
            .setCorrelationId("1234")
            .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
            .build();
    build.setConsumerTag("msg");
    CorrelationData dataSend = new CorrelationData();
    dataSend.setId("1234");

    // 用于回调验证的消息
    byte[] returnBytes = "这是响应的消息:".getBytes(StandardCharsets.UTF_8);
    dataSend.setReturnedMessage(new Message(returnBytes, null));

    // 发送的消息
    byte[] sendBytes = "这是等待确认的消息".getBytes(StandardCharsets.UTF_8);
    Message message = new Message(sendBytes, build);
    template.convertAndSend("confirm.ex", "confirm.rk", message, dataSend);

    return "ok";
  }

  @RequestMapping("/bizFalse")
  public String doInvokeFalse() {
    MessageProperties build =
        MessagePropertiesBuilder.newInstance()
            .setHeader("key1", "value1")
            .setCorrelationId("1234")
            .setContentEncoding(StandardCharsets.UTF_8.name())
            .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
            .build();
    build.setCorrelationId("1234");
    build.setConsumerTag("msg1");

    Message msg = new Message("这是等待确认的消息:".getBytes(StandardCharsets.UTF_8), build);

    template.convertAndSend("confirm.ex.bizfalse", "biz", msg);

    return "ok";
  }
}
