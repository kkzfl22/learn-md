package com.nullnull.learn;

import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author liujun
 * @since 2023/8/19
 */
public class ListenerApplication {

    public static void main(String[] args) {
        //启动Spring容器
        new ClassPathXmlApplicationContext("spring-rabbit.xml");
    }
}
