package com.nullnull.learn;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * 主类
 *
 * @author liujun
 * @since 2023/8/20
 */
public class ConsumerListenerApplication {


    public static void main(String[] args) {
        new AnnotationConfigApplicationContext(RabbitConfig.class);
    }

}
