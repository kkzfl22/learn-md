package com.nullnull.learn.consumer;

import com.nullnull.learn.product.RabbitConfig;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.AbstractApplicationContext;

import java.io.UnsupportedEncodingException;

/**
 * @author liujun
 * @since 2023/8/20
 */
public class ConsumerGetApplication {

  public static void main(String[] args) throws Exception {
    // 从指定类加载配制信息
    AbstractApplicationContext context = new AnnotationConfigApplicationContext(RabbitConfig.class);
    RabbitTemplate rabbit = context.getBean(RabbitTemplate.class);

    for (int i = 0; i < 100; i++) {
      try {
        Message receive = rabbit.receive("queue.anno");
        String encoding = receive.getMessageProperties().getContentEncoding();
        System.out.println("消息信息：" + new String(receive.getBody(), encoding));
      } catch (UnsupportedEncodingException e) {
        e.printStackTrace();
      } catch (Exception e) {
        e.printStackTrace();
      }

      try {
        Thread.sleep(1000l);
      } catch (Exception e) {
        e.printStackTrace();
      }
    }

    context.close();
  }
}
