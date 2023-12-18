package com.nullnull.learn.service;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.nullnull.learn.entity.DataUserPO;
import com.nullnull.learn.mapper.DataUserMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author liujun
 * @since 2023/12/18
 */
@Service
public class DataUserServiceImpl implements DataUserService {

    @Autowired
    private DataUserMapper userMapper;

    @Override
    public List<DataUserPO> queryUsers() {
        return userMapper.selectList(Wrappers.emptyWrapper());
    }
}
