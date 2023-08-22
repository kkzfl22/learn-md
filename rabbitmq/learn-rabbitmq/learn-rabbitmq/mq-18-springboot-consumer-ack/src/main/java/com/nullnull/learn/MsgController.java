package com.nullnull.learn;

import com.rabbitmq.client.Channel;
import com.rabbitmq.client.GetResponse;
import org.springframework.amqp.rabbit.core.ChannelCallback;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;
import java.util.concurrent.ThreadLocalRandom;

/**
 * 消息控制器
 *
 * @author liujun
 * @since 2023/8/22
 */
@RestController
public class MsgController {

  @Autowired private RabbitTemplate rabbitTemplate;

  @RequestMapping("/msg")
  public String getMessage() {
    String message =
        rabbitTemplate.execute(
            new ChannelCallback<String>() {
              @Override
              public String doInRabbit(Channel channel) throws Exception {

                GetResponse getResponse = channel.basicGet("ack.qu", false);

                if (null == getResponse) {
                  return "你已经消费完所有的消息";
                }

                String message = new String(getResponse.getBody(), StandardCharsets.UTF_8);

                if (ThreadLocalRandom.current().nextInt(10) % 3 == 0) {
                  // 执行消息确认操作
                  channel.basicAck(getResponse.getEnvelope().getDeliveryTag(), false);

                  return "已确认的消息:" + message;
                } else {
                  // 拒收一条消息并重新放回队列
                  channel.basicReject(getResponse.getEnvelope().getDeliveryTag(), true);
                  return "拒绝的消息:" + message;
                }
              }
            });

    return message;
  }
}
