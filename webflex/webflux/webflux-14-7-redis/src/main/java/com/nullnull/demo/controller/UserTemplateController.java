package com.nullnull.demo.controller;

import com.nullnull.demo.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.concurrent.TimeUnit;

/**
 * 用户操作
 *
 * @author nullnull
 * @since 2024/12/18
 */
@RestController
@RequestMapping("/user/template")
public class UserTemplateController {

    /**
     * 使用同步的RedisTemplate来操作
     */
    @Autowired
    private RedisTemplate redisTemplate;


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
        redisTemplate.opsForValue().set(PREFIX + user.getId(), user, 120, TimeUnit.SECONDS);
        return Mono.create(monoSink -> monoSink.success(user));
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
        Boolean deleteRsp = redisTemplate.delete(key);
        return Mono.create(monoSink ->
                {
                    if (deleteRsp) {
                        monoSink.success(id);
                    } else {
                        monoSink.success(null);
                    }
                }
        );
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
        ValueOperations<String, User> operations = redisTemplate.opsForValue();
        User user = operations.get(key);
        if (null != user) {
            return Mono.create(userMonoSink -> userMonoSink.success(user));
        }
        return Mono.create(userMonoSink -> userMonoSink.success(null));
    }


}
