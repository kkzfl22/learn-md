package com.nullnull.learn.service;

import com.nullnull.learn.entity.DataUserPO;

import java.util.List;

/**
 * @author liujun
 * @since 2023/12/18
 */
public interface DataUserService {

    /**
     * 查询所有用户信息
     *
     * @return
     */
    public List<DataUserPO> queryUsers();

}
