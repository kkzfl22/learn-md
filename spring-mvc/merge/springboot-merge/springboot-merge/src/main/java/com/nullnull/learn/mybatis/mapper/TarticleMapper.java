package com.nullnull.learn.mybatis.mapper;

import com.nullnull.learn.mybatis.pojo.Tarticle;import com.nullnull.learn.mybatis.pojo.Tarticle;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;
/**
 * 文章信息
 *
 * @author liujun
 * @since 2023/3/23
 */
public interface TarticleMapper {

  @Select("select * from t_article where id = #{id}")
  Tarticle queryById(Integer id);
}
