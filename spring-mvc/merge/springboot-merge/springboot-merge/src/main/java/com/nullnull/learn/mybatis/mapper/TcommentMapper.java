package com.nullnull.learn.mybatis.mapper;

import com.nullnull.learn.mybatis.pojo.Tcomment;import com.nullnull.learn.mybatis.pojo.Tcomment;
import org.apache.ibatis.annotations.Mapper;

/**
 * 评论的数据库映射文件
 *
 * @author liujun
 * @since 2023/3/23
 */
public interface TcommentMapper {

  /**
   * 按ID查询评论
   *
   * @param id
   * @return
   */
  Tcomment queryById(Integer id);
}
