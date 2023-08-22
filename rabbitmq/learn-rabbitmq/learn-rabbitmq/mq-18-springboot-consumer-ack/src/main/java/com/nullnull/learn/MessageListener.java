package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.support.AmqpHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 消息监听器
 *
 * @author liujun
 * @since 2023/8/22
 */
@Component
public class MessageListener {

  /**
   * NONE模式，则只要收到消息后就立即确认（消息出列，标识已消费），有丢数据风险
   *
   * <p>AUTO模式，看情况确认，如果此时消费者抛出异常则消息会返回队列中
   *
   * <p>WANUAL模式，需要显示的调用当前channel的basicAck方法
   *
   * @param channel
   * @param deliveryTag
   * @param msg
   */
  @RabbitListener(queues = "ack.qu", ackMode = "AUTO")
  public void handMessageTopic(
      Channel channel, @Header(AmqpHeaders.DELIVERY_TAG) long deliveryTag, @Payload String msg) {

    System.out.println("消息内容：" + msg);

    ThreadLocalRandom current = ThreadLocalRandom.current();

    try {
      if (current.nextInt(10) % 3 != 0) {
        // 手动nack，告诉broker消费者处理失败，最后一个参数表示是否需要将消息重新入列
        // channel.basicNack(deliveryTag, false, true);
        // 手动拒绝消息，第二个参数表示是否重新入列
        channel.basicReject(deliveryTag, true);
      } else {
        // 手动ACK，deliveryTag表示消息的唯一标志，multiple表示是否批量确认
        channel.basicAck(deliveryTag, false);
        System.out.println("已经确认的消息" + msg);
      }
    } catch (IOException e) {
      e.printStackTrace();
    }
  }
}
