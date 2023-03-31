package com.nullnull.learn.mybatis.mapper;

import com.nullnull.learn.mybatis.pojo.Tcomment;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
/**
 * 测试评价
 *
 * @author liujun
 * @since 2023/3/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTcommentMapper {

  @Autowired private TcommentMapper tcommentMapper;

  @Test
  public void queryById() {
    Tcomment comments = tcommentMapper.queryById(1);
    System.out.println(comments);
    Assert.assertNotNull(comments);
  }
}
