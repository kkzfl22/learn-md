package com.nullnull.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

import java.util.UUID;

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
  private String name;

  @Column("money")
  private Integer money;

  /** 制造年份 */
  @Column("make_year")
  private Integer makeYear;

  public Computer(String name, Integer money, Integer makeYear) {
    this.code = UUID.randomUUID().toString();
    this.name = name;
    this.money = money;
    this.makeYear = makeYear;
  }

  public Computer() {
    this(null, null, null);
  }
}
