package com.nullnull.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
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
@Getter
@Setter
@ToString
public class Computer {

  @Id private String code;

  @Column("name")
  private String title;

  @Column("money")
  private Integer money;

  /** 制造年份 */
  @Column("make_year")
  private Integer makeYear;

  public Computer(String code, String title, Integer money, Integer makeYear) {
    this.code = code;
    this.title = title;
    this.money = money;
    this.makeYear = makeYear;
  }

  public Computer() {
    this(null, null, null, null);
  }
}
