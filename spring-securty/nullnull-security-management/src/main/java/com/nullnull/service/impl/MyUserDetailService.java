package com.nullnull.service.impl;

import com.nullnull.domain.Permission;
import com.nullnull.domain.User;
import com.nullnull.service.PermissionService;
import com.nullnull.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 基于数据库完成认证
 *
 * @author liujun
 * @since 2023/6/7
 */
@Service
public class MyUserDetailService implements UserDetailsService {

    /**
     * 用户服务
     */
    @Autowired
    private UserService userService;

    @Autowired
    private PermissionService permissionService;

    /**
     * 根据用户名查询用户信息
     *
     * @param username
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        User userRsp = userService.findByUsername(username);

        if (null == userRsp) {
            throw new UsernameNotFoundException("user not found " + username);
        }
        // 构建用户结果信息
        //// 权限集合
        //Collection<? extends GrantedAuthority> authorities = new ArrayList<>();
        //UserDetails userDetailRsp =
        //    new org.springframework.security.core.userdetails.User(
        //        //  用户名
        //        username,
        //        // 不使用密码加密
        //        "{noop}" + userRsp.getPassword(),
        //        // 用户是否启动
        //        true,
        //        // 用户是否过期
        //        true,
        //        // 用户凭证是否过期
        //        true,
        //        // 用户是否锁定
        //        true,
        //        // 权限
        //        authorities);

        //使用密码加密,PasswordEncoderFactories中定义了使用的密码方式
        //推荐使用bcrypt，此方式，使用强哈希算法，并且每次结果都不一样，相比于MD5,运算时间更长，到百毫秒级
        // 权限集合
        Collection<GrantedAuthority> authorities = new ArrayList<>();

        ////对用户设置权限信息
        //if ("admin".equalsIgnoreCase(userRsp.getUsername())) {
        //    authorities.add(new SimpleGrantedAuthority("ROLE_ADMIN"));
        //} else {
        //    authorities.add(new SimpleGrantedAuthority("ROLE_PRODUCT"));
        //}

        //查询用户对应的授权权限集合
        List<Permission> permissionList = permissionService.findByUserId(userRsp.getId());
        for (Permission permission : permissionList) {
            //授权
            authorities.add(new SimpleGrantedAuthority(permission.getPermissionTag()));
        }


        UserDetails userDetailRsp =
                new org.springframework.security.core.userdetails.User(
                        //  用户名
                        username,
                        // 使用bcrypt加密
                        "{bcrypt}" + userRsp.getPassword(),
                        // 用户是否启动
                        true,
                        // 用户是否过期
                        true,
                        // 用户凭证是否过期
                        true,
                        // 用户是否锁定
                        true,
                        // 权限
                        authorities);

        return userDetailRsp;
    }
}