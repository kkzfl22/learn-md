package com.nullnull.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * @author nullnull
 * @since 2024/12/18
 */
@Data
public class City implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private Long id;

    /**
     * 省份编号
     */
    private Long provinceId;

    /**
     * 城市名称
     */
    private String cityName;

    /**
     * 描述
     */
    private String description;
}
