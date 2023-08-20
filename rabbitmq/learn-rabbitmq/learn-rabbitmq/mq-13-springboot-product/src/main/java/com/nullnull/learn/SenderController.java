package com.nullnull.learn;

import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageBuilder;
import org.springframework.amqp.core.MessageProperties;
import org.springframework.amqp.core.MessagePropertiesBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;

import java.nio.charset.StandardCharsets;

/**
 * 进行消息的发送操作
 *
 * @author liujun
 * @since 2023/8/20
 */
@Controller
public class SenderController {


    @Autowired
    private AmqpTemplate template;

    @GetMapping("/send/{msg}")
    @ResponseBody
    public String sendMsg(@PathVariable String msg) {
        MessageProperties properties = MessagePropertiesBuilder.newInstance()
                .setContentEncoding(StandardCharsets.UTF_8.name())
                .setHeader("key", "value").build();

        Message msgData = MessageBuilder.withBody(msg.getBytes(StandardCharsets.UTF_8))
                .andProperties(properties)
                .build();

        template.send("boot.ex", "boot.rk", msgData);

        return "successMsg";
    }

}
