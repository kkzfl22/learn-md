package com.nullnull.demo.controller;

import com.nullnull.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.thymeleaf.spring5.context.webflux.ReactiveDataDriverContextVariable;
import reactor.core.publisher.Flux;

import java.time.Duration;
import java.util.ArrayList;
import java.util.List;

/**
 * 用户操作
 *
 * @author nullnull
 * @since 2024/12/11
 */
@Controller
public class UserController {

    @RequestMapping("/user-list-view-ftl")
    public String getUserList(final Model model) {
        List<User> dataList = new ArrayList<>(20);
        for (int i = 0; i < 10; i++) {
            User user = new User();
            user.setName("name:" + i);
            user.setAge(i);
            user.setAddress("地址：" + i);
            dataList.add(user);
        }

        final Flux<User> userListStream = Flux.fromIterable(dataList).delayElements(Duration.ofMillis(1000));
        model.addAttribute("userList",
                new ReactiveDataDriverContextVariable(userListStream, 1, 1)
        );
        return "thymeleaf/user-list-view";
    }

}
