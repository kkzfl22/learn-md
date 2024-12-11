package org.example.webflux.service;

import org.example.webflux.entity.Profile;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/11
 */
public interface ProfileService {


    /**
     * 查询信息
     *
     * @param name 用户名
     * @return 信息
     */
    Mono<Profile> getByUser(String name);
}
