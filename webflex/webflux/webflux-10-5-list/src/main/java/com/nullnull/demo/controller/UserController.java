package com.nullnull.demo.controller;

import com.nullnull.demo.entity.User;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

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
    public Mono<String> getUserList(final Model model) {
        List<User> dataList = new ArrayList<>(5);
        for (int i = 0; i < 5; i++) {
            User user = new User();
            user.setName("name:" + i);
            user.setAge(i);
            user.setAddress("地址：" + i);
            dataList.add(user);
        }

        final Flux<User> userListStream = Flux.fromIterable(dataList);
        return userListStream.collectList()
                .doOnNext(list -> model.addAttribute("userList", list))
                .then(Mono.just("/freemarker/user-list-view"));
    }

}
