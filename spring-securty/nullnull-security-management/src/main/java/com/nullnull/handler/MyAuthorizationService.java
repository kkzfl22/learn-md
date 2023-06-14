package com.nullnull.handler;

import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;

/**
 * 自定义授权方法
 *
 * @author liujun
 * @since 2023/6/10
 */
@Component
public class MyAuthorizationService {

    /**
     * 检查用户是否有对应的访问权限
     *
     * @param auth    登录用户
     * @param request 请求对象
     * @return
     */
    public boolean check(Authentication auth, HttpServletRequest request) {

        User user = (User) auth.getPrincipal();
        //获取用户所有权限
        Collection<GrantedAuthority> authorities = user.getAuthorities();
        //获取用户名
        String username = user.getUsername();

        //如果用户名为admin,则不需要认证
        if (username.equalsIgnoreCase("admin")) {
            return true;
        } else {
            //循环用户的权限，判断是否有ROLE_ADMIN权限，有则返回true
            for (GrantedAuthority authority : authorities) {
                String role = authority.getAuthority();
                if ("ROLE_ADMIN".equals(role)) {
                    return true;
                }
            }
        }


        return false;
    }


    /**
     * 检查用户是否有对应的访问权限
     *
     * @param auth 登录用户
     * @param request 请求
     * @return
     */
    public boolean numCheck(Authentication auth, HttpServletRequest request,Integer id) {
        if(id > 8)
        {
            return false;
        }

        return false;
    }


}
