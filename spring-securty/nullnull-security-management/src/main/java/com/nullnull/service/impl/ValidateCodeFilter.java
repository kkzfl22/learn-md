package com.nullnull.service.impl;

import com.nullnull.controller.ValidateCodeController;
import com.nullnull.exception.ValidCodeException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 验证码验证的Filter，需要继承OncePerRequestFilter，确保在一次请求只通过一次filter，而不是重复执行
 *
 * @author liujun
 * @since 2023/6/8
 */
@Component
public class ValidateCodeFilter extends OncePerRequestFilter {


    @Autowired
    private StringRedisTemplate redisTemplate;


    @Autowired
    private MyAuthenticationService loginAuth;


    @Override
    protected void doFilterInternal(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse,
                                    FilterChain filterChain) throws ServletException, IOException {

        //判断当前为登录请求，则进行验证
        if (httpServletRequest.getRequestURI().equals("/login")
                && httpServletRequest.getMethod().equalsIgnoreCase("post")) {
            //执行检查的逻辑操作
            try {
                valid(httpServletRequest);
            } catch (ValidCodeException e) {
                loginAuth.onAuthenticationFailure(httpServletRequest, httpServletResponse, e);
                return;
            }
        }
        filterChain.doFilter(httpServletRequest, httpServletResponse);

    }

    private void valid(HttpServletRequest request) {
        String code = request.getParameter("imageCode");

        String ip = request.getRemoteAddr();

        String redisKey = ValidateCodeController.REDIS_KEY_IMAGE_CODE + "-" + ip;
        String redisValue = redisTemplate.boundValueOps(redisKey).get();

        //验证码为空判断
        if (StringUtils.isEmpty(code)) {
            throw new ValidCodeException("验证码不能为空");
        }

        //验证码过期检查
        if (null == redisValue) {
            throw new ValidCodeException("验证码已过期");
        }

        //验证码不正确
        if (!code.equals(redisValue)) {
            throw new ValidCodeException("验证码不正确");
        }

        //如果正确，则需要将验证码做删除操作
        redisTemplate.delete(redisKey);

    }
}
