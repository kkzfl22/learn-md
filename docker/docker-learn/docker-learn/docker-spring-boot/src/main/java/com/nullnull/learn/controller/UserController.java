package com.nullnull.learn.controller;

import com.nullnull.learn.entity.DataUserPO;
import com.nullnull.learn.service.DataUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

/**
 * @author liujun
 * @since 2023/12/18
 */
@RestController
public class UserController {


    @Autowired
    private DataUserService userService;

    @GetMapping("/users")
    @ResponseBody
    public List<DataUserPO> dataList() {
        List<DataUserPO> dataUserList = userService.queryUsers();
        return dataUserList;
    }

}
