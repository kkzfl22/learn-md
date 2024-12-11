package org.example.webflux.service;

import org.example.webflux.entity.Profile;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * @author nullnull
 * @since 2024/12/11
 */
@Service
public class ProfileServiceImpl implements ProfileService {

    @Override
    public Mono<Profile> getByUser(String name) {
        return Mono.just(new Profile(name, name + "的描述信息"));
    }
}
