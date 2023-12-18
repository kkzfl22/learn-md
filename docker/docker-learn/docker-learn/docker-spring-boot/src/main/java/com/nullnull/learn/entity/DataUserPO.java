package com.nullnull.learn.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

/**
 * @author liujun
 * @since 2023/12/18
 */
@Setter
@Getter
@ToString
@TableName("data_user")
public class DataUserPO {

    @TableId("userid")
    private Integer userId;

    @TableField("username")
    private String userName;

    @TableField("password")
    private String password;

    @TableField("userroles")
    private String userRoles;

    @TableField("nickname")
    private String nickName;

}
