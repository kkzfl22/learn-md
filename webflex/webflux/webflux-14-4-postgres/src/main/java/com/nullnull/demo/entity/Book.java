package com.nullnull.demo.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.data.relational.core.mapping.Table;

/**
 * @author nullnull
 * @since 2024/12/14
 */
@Table("book")
@Getter
@Setter
@ToString
public class Book {

    @org.springframework.data.annotation.Id
    private Integer Id;

}
