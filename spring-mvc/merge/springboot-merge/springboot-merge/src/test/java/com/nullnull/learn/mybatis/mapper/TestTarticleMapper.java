package com.nullnull.learn.mybatis.mapper;

import com.nullnull.learn.mybatis.pojo.Tarticle;import com.nullnull.learn.mybatis.pojo.Tarticle;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * 测试使用注解方式的的调用
 *
 * @author liujun
 * @since 2023/3/23
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class TestTarticleMapper {

  @Autowired private TarticleMapper tarticleMapper;

  @Test
  public void testRunData() {
    Tarticle tarticle = tarticleMapper.queryById(1);
    System.out.println(tarticle);
    Assert.assertNotNull(tarticle);
  }
}
