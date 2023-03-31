package com.nullnull.learn.mybatis.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 评论表信息
 *
 * @author liujun
 * @since 2023/3/23
 */
@Getter
@Setter
@ToString
public class Tcomment {

  /** 主键的id */
  private Integer id;

  /** 评论的内容 */
  private String content;

  /** 作者 */
  private String author;

  /** 关联T_article表的信息 */
  private Integer aId;
}
