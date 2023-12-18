package com.nullnull.learn.service;

import com.nullnull.learn.entity.DataUserPO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

/**
 * 测试用户操作
 *
 * @author liujun
 * @since 2023/12/18
 */
@SpringBootTest
public class TestDataUserServiceImpl {

    @Autowired
    private DataUserService userService;


    @Test
    public void getAll() {
        List<DataUserPO> dataUser = userService.queryUsers();
        Assertions.assertNotEquals(0, dataUser.size());
    }

}
