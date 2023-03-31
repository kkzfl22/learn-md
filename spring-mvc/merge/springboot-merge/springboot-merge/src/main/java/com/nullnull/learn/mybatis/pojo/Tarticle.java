package com.nullnull.learn.mybatis.pojo;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
/**
 * 文章的实体信息
 *
 * @author liujun
 * @since 2023/3/23
 */
@Getter
@Setter
@ToString
public class Tarticle {

  /** id */
  private Integer id;

  /** 文章标题 */
  private String title;

  /** 文章内容 */
  private String content;
}
