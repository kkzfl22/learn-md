package com.nullnull.learn;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.nio.charset.StandardCharsets;

/**
 * @author liujun
 * @since 2023/8/29
 */
@RestController
public class BizController {

  @Autowired private AmqpTemplate template;

  @RequestMapping("/expire-dlx")
  public String expireDlx() {
    String msg = "测试发送消息，10秒超时";
    template.convertAndSend(
        "dlx.spring.biz.ex", "dlx.spring.biz.rk", msg.getBytes(StandardCharsets.UTF_8));

    return "expire-dlx";
  }

  @RequestMapping("/dlx/get")
  public String sendDlxMsg() {
    byte[] getMsg = (byte[]) (template.receiveAndConvert("dlx.springboot.expire.qu"));

    return new String(getMsg, StandardCharsets.UTF_8);
  }
}
