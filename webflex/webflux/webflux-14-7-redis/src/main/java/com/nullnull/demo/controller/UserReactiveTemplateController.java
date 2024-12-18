package com.nullnull.demo.controller;

import com.nullnull.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.ReactiveRedisTemplate;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 用户操作
 *
 * @author nullnull
 * @since 2024/12/18
 */
@RestController
@RequestMapping("/user/reactive")
public class UserReactiveTemplateController {

    /**
     * 使用异步的RedisTemplate来操作
     */
    @Autowired
    private ReactiveRedisTemplate reactiveRedisTemplate;


    /**
     * 前缀信息
     */
    private static final String PREFIX = "user_";

    /**
     * 添加用户
     *
     * @param user
     */
    @PostMapping
    public Mono<User> addUser(@RequestBody User user) {
        return reactiveRedisTemplate.opsForValue().getAndSet(PREFIX + user.getId(), user);
    }

    /**
     * 删除用户
     *
     * @param id
     * @return
     */
    @DeleteMapping(value = "/{id}")
    public Mono<Long> deleteUser(@PathVariable("id") Long id) {
        String key = PREFIX + id;
        return reactiveRedisTemplate.opsForValue().delete(key);
    }

    /**
     * 查询用户
     *
     * @param id
     * @return
     */
    @GetMapping(value = "/{id}")
    public Mono<User> queryById(@PathVariable("id") Long id) {
        String key = PREFIX + id;

        return reactiveRedisTemplate.opsForValue().get(key);
    }


}
