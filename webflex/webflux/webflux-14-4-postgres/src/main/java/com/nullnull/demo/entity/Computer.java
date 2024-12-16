package com.nullnull.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

/**
 * 电脑信息
 *
 * @author nullnull
 * @since 2024/12/14
 */
@Table("computer")
@Data
public class Computer {

  /**
   * @Id 仅针对自增长序列
   */
  @Id private Integer id;

  @Column("name")
  private String name;

  @Column("money")
  private Integer money;

  /** 制造年份 */
  @Column("make_year")
  private Integer makeYear;

  public Computer(String name, Integer money, Integer makeYear) {
    this.name = name;
    this.money = money;
    this.makeYear = makeYear;
  }

  public Computer() {
    this(null, null, null);
  }
}
