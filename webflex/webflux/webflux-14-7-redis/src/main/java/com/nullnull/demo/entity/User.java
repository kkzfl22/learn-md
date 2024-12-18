package com.nullnull.demo.entity;

import lombok.Data;
import org.springframework.data.annotation.Id;

import java.io.Serializable;

/**
 * 用户信息
 *
 * @author nullnull
 * @since 2024/12/18
 */
@Data
public class User implements Serializable {

    private static final long serialVersionUID = -1L;

    @Id
    private Long id;

    /**
     * 用户编号
     */
    private String code;

    /**
     * 用户所在的城市
     */
    private String addressCity;

    /**
     * 备注
     */
    private String remark;
}
