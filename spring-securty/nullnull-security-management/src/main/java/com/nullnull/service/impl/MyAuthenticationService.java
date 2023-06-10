package com.nullnull.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Service;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 自定义登录成功或失败处理器
 *
 * @author liujun
 * @since 2023/6/8
 */
@Service
public class MyAuthenticationService implements AuthenticationSuccessHandler,
        AuthenticationFailureHandler,
        LogoutSuccessHandler {


    /**
     * 跳转处理
     */
    private RedirectStrategy redirect = new DefaultRedirectStrategy();


    @Autowired
    private ObjectMapper objectMapper;

    /**
     * 登录成功后的处理逻辑
     *
     * @param request        the request which caused the successful authentication
     * @param response       the response
     * @param authentication the <tt>Authentication</tt> object which was created during
     *                       the authentication process.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {
        System.out.println("登录成功后继续处理");
        //redirect.sendRedirect(request,response,"/");
        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpStatus.OK.value());
        result.put("message", "登录成功");

        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }

    /**
     * 失败的处理逻辑
     *
     * @param request   the request during which the authentication attempt occurred.
     * @param response  the response.
     * @param exception the exception which was thrown to reject the authentication
     *                  request.
     * @throws IOException
     * @throws ServletException
     */
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException, ServletException {

        System.out.println("登录失败后继续处理");
        //redirect.sendRedirect(request,response,"/toLoginPage");
        Map<String, Object> result = new HashMap<>();
        result.put("code", HttpStatus.UNAUTHORIZED.value());
        result.put("message", exception.getMessage());

        response.setContentType("application/json;charset=UTF-8");

        response.getWriter().write(objectMapper.writeValueAsString(result));
    }


    @Override
    public void onLogoutSuccess(HttpServletRequest request, HttpServletResponse response,
                                Authentication authentication) throws IOException, ServletException {
        System.out.println("收到退出之后的处理");
        //response.setContentType("text/html;charset=UTF-8");
        redirect.sendRedirect(request, response, "/toLoginPage");
    }
}
