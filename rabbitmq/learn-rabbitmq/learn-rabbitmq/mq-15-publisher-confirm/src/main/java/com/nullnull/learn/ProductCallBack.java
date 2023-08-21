package com.nullnull.learn;

import com.rabbitmq.client.AMQP;
import com.rabbitmq.client.BuiltinExchangeType;
import com.rabbitmq.client.Channel;
import com.rabbitmq.client.ConfirmCallback;
import com.rabbitmq.client.Connection;
import com.rabbitmq.client.ConnectionFactory;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ConcurrentNavigableMap;
import java.util.concurrent.ConcurrentSkipListMap;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.TimeoutException;

/**
 * 消息的生产者
 *
 * @author liujun
 * @since 2023/8/21
 */
public class ProductCallBack {

  public static void main(String[] args) throws Exception {
    ConnectionFactory factory = new ConnectionFactory();
    factory.setUri("amqp://root:123456@node1:5672/%2f");

    Connection connection = factory.newConnection();
    Channel channel = connection.createChannel();

    try {
      // 开启发送方确认机制
      AMQP.Confirm.SelectOk selectOk = channel.confirmSelect();

      channel.exchangeDeclare("confirm.ex", BuiltinExchangeType.DIRECT);
      channel.queueDeclare("confirm.qe", false, false, false, null);
      channel.queueBind("confirm.qe", "confirm.ex", "confirm.rk");

      // 记录下发送与确认的消息
      ConcurrentNavigableMap<Long, String> ackConfirmMap = new ConcurrentSkipListMap<>();
      ConcurrentNavigableMap<Long, String> nackConfirmMap = new ConcurrentSkipListMap<>();

      // ack确认的信息
      ConfirmCallback ackCallback =
          new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
              if (multiple) {
                // 获取已经被确认的Map集合
                ConcurrentNavigableMap<Long, String> headMap =
                    ackConfirmMap.headMap(deliveryTag, true);
                long threadId = Thread.currentThread().getId();
                System.out.println(
                    "【ack确认】线程ID："
                        + threadId
                        + "，批量：小于等于:"
                        + deliveryTag
                        + "的消息都被确认了，内容："
                        + headMap.keySet());
                headMap.clear();
              } else {
                long threadId = Thread.currentThread().getId();
                ackConfirmMap.remove(deliveryTag);
                System.out.println(
                    "【ack确认】线程ID：" + threadId + "，单条:" + deliveryTag + "对应的消息被确认,key:" + deliveryTag);
              }
            }
          };

      // 不确认的消息
      ConfirmCallback nackCallBack =
          new ConfirmCallback() {
            @Override
            public void handle(long deliveryTag, boolean multiple) throws IOException {
              if (multiple) {

                ConcurrentNavigableMap<Long, String> headMap =
                    ackConfirmMap.headMap(deliveryTag, true);
                long threadId = Thread.currentThread().getId();
                System.out.println(
                    "【nack确认】线程ID："
                        + threadId
                        + "，批量：小于等于"
                        + deliveryTag
                        + "的消息都不被确认了,内容:"
                        + headMap.keySet());
                nackConfirmMap.putAll(headMap);
                headMap.clear();
              } else {
                long threadId = Thread.currentThread().getId();
                System.out.println("【nack确认】线程的ID：" + threadId + "，单条:" + deliveryTag + "对应的消息被确认");
                String value = ackConfirmMap.remove(deliveryTag);
                nackConfirmMap.put(deliveryTag, value);
              }
            }
          };

      channel.addConfirmListener(ackCallback, nackCallBack);

      // 执行数据发送
      try {
        for (int i = 0; i < 28; i++) {
          // 获取当前发送的序列号
          long nextPublishSeqNo = channel.getNextPublishSeqNo();
          String pushMsg = "序列号:" + nextPublishSeqNo + ":已经发送了消息信息:" + (i + 1);
          channel.basicPublish(
              "confirm.ex", "confirm.rk", null, pushMsg.getBytes(StandardCharsets.UTF_8));
          ackConfirmMap.put(nextPublishSeqNo, pushMsg);
          long threadId = Thread.currentThread().getId();
          System.out.println("【发送】线程的ID：" + threadId + "，序号：" + nextPublishSeqNo + "发送完毕");

          // 随机休眠
          Thread.sleep(ThreadLocalRandom.current().nextInt(0, 5));
        }
      } catch (IOException e) {
        e.printStackTrace();
        System.out.println("消息被拒绝:");
      }

      Thread.sleep(5000);

      System.out.println("确认的消息:" + ackConfirmMap.size());
      System.out.println("未确认的消息:" + nackConfirmMap.size());

    } catch (IOException e) {
      e.printStackTrace();
    } catch (InterruptedException e) {
      e.printStackTrace();
    } finally {
      channel.close();
      connection.close();
    }
  }
}
