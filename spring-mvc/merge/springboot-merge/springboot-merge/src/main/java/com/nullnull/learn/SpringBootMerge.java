package com.nullnull.learn;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
/**
 * @author liujun
 * @since 2023/3/23
 */
@SpringBootApplication
@MapperScan(value = {"com.nullnull.learn.mapper"})
public class SpringBootMerge {

  public static void main(String[] args) {
    SpringApplication.run(SpringBootMerge.class, args);
  }
}
