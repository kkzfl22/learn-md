package com.nullnull.learn.product;

import java.nio.charset.StandardCharsets;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

/**
 * @author liujun
 * @since 2023/8/19
 */
public class ProducterApplication {

  public static void main(String[] args) throws Exception {
    AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);

    RabbitTemplate template = context.getBean(RabbitTemplate.class);

    MessageProperties msgBuild =
        MessagePropertiesBuilder.newInstance()
            .setContentType(MessageProperties.CONTENT_TYPE_TEXT_PLAIN)
            .setContentEncoding(StandardCharsets.UTF_8.name())
            .setHeader("test.header", "test.value")
            .build();

    for (int i = 0; i < 200; i++) {
      Message msg =
          MessageBuilder.withBody(("你好 RabbitMQ! id :" + i).getBytes(StandardCharsets.UTF_8))
              .andProperties(msgBuild)
              .build();

      template.send("ex.anno.fanout", "routing.anno", msg);

      // Thread.sleep(1000L);
    }
    System.out.println("发送完毕");

    context.close();
  }
}
