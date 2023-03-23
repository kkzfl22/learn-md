package com.nullnull.learn.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 自定义Starter的配制类
 *
 * @author liujun
 * @since 2023/3/19
 */
@Configuration
// 当类路径classpath下指定的情况，就会进行自动配制。
@ConditionalOnClass
// @ConditionalOnClass(SimpleBean.class)
public class MyAutoConfiguration {

  static {
    System.out.println("MyAutoConfiguration init.....");
  }

  @Bean
  public SimpleBean simpleBean() {
    return new SimpleBean();
  }
}
