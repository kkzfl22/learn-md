package org.example.webflux.controller;

import org.example.webflux.entity.Profile;
import org.example.webflux.service.ProfileService;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.ReactiveSecurityContextHolder;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

/**
 * 安全访问的控制器
 *
 * @author nullnull
 * @since 2024/12/11
 */
@RestController
@RequestMapping("api/v1")
public class SecurityProfileController {


    private final ProfileService profileService;

    public SecurityProfileController(ProfileService profileService) {
        this.profileService = profileService;
    }

    /**
     * @PreAuthorize 注解用于访问控制，检查Authentication 是否具有所需的角色。
     * @return
     */
    @GetMapping("/profiles")
    @PreAuthorize("hasRole('admin')")
    public Mono<Profile> getProfile() {
        return ReactiveSecurityContextHolder
                //访问当前的SecurityContext
                .getContext()
                //从SecurityContext中获取认证信息
                .map(SecurityContext::getAuthentication)
                //访问用户个人信息
                .flatMap(auth -> profileService.getByUser(auth.getName()));
    }

}
