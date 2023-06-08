package com.nullnull.config;

import com.nullnull.service.impl.MyAuthenticationService;
import com.nullnull.service.impl.MyUserDetailService;
import com.nullnull.service.impl.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.DefaultRedirectStrategy;
import org.springframework.security.web.RedirectStrategy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;

import javax.sql.DataSource;

/**
 * security的配制
 *
 * <p>可以认为 HttpSecurity 是 WebSecurity 的一部分， WebSecurity 是包含 HttpSecurity 的更大 的一个概念
 *
 * @author liujun
 * @since 2023/6/7
 */
@Configuration
public class SecurityConfiguration extends WebSecurityConfigurerAdapter {

    /**
     * 用户信息
     */
    @Autowired
    private MyUserDetailService userDetailsService;


    /**
     * 登录成功或者失败的处理
     */
    @Autowired
    private MyAuthenticationService authenticationService;


    /**
     * 验证码的过滤器
     */
    @Autowired
    private ValidateCodeFilter validateCodeFilter;


    /**
     * 身份安全管理器
     *
     * @param auth
     * @throws Exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService);
    }

    /**
     * WebSecurity 不仅通过 HttpSecurity 定义某些请求的安全控制，也通过其他方式定义其他某些 请求可以忽略安全控制
     *
     * @param web
     * @throws Exception
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        web.ignoring().antMatchers("/css/**", "/images/**", "/js/**", "/code/**");
    }

    /**
     * HTTP的请求处理
     *
     * <p>HttpSecurity 仅用于定义需要安全控制的请求(当然 HttpSecurity 也可以指定某些请求不需要 安全控制);
     *
     * @param http
     * @throws Exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        // 现在已经基本不再使用，Base可以被破解
        //// 使用httpBasic认证
        // http.httpBasic()
        //    .and()
        //    // 所有请求都需要认证
        //    .authorizeRequests()
        //    .anyRequest()
        //    .authenticated();

        // 开启认证，并且所有请求都需要认证
        // http.formLogin().and().authorizeRequests().anyRequest().authenticated();

        // 问题1：
        // 该网页无法正常运作localhost 将您重定向的次数过多。
        // 尝试清除 Cookie.
        // ERR_TOO_MANY_REDIRECTS
        // 所有的请求的
        // 此问题是因为登录页被拦截，而陷入了死循环，需要将login.html给放行
        // .antMatchers("/login.html").permitAll()、

        // 问题2：
        // Whitelabel Error Page
        // This application has no explicit mapping for /error, so you are seeing this as a fallback.
        //
        //        Wed Jun 07 16:57:19 CST 2023
        // There was an unexpected error (type=Not Found, status=404).
        //        No message available
        // 由于使用了SpringBoot整合thymeleaf,所有静态资源都放在resource/templates下面，直接访问模板不能加载，改为后端加载
        // 将.loginPage("/login.html")改为.loginPage("/toLoginPage"),同时也得放行
        // 同时全带来第3个问题，没有样式
        // web.ignoring().antMatchers("/css/**", "/images/**", "/js/**");
        //
        //http.formLogin()
        //        // 自定义登录页面
        //        .loginPage("/toLoginPage")
        //        // 表单提交路径,及指定提交的用户名和密码
        //        .loginProcessingUrl("/login")
        //        .usernameParameter("username")
        //        .passwordParameter("password")
        //        // 登录成功后的跳转路径
        //        .successForwardUrl("/")
        //        .and()
        //        //记住我功能打开
        //        //如果直接配制，则是将md5(数据+过期时间+密码直接)直接保存在本地cookie中，安全性不好，
        //        .rememberMe()
        //        //默认的失效时间是2周
        //        .tokenValiditySeconds(1209600)
        //        //自定义静音记住我的的值
        //        .rememberMeParameter("remember-me")
        //        .and()
        //        //权限设置
        //        .authorizeRequests()
        //        // 放行登录页面
        //        .antMatchers("/toLoginPage")
        //        .permitAll()
        //        .anyRequest()
        //        .authenticated();

        //将验证码的处理加入过滤器链
        http.addFilterBefore(validateCodeFilter, UsernamePasswordAuthenticationFilter.class);

        //记住我功能使用数据库
        http.formLogin()
                // 自定义登录页面
                .loginPage("/toLoginPage")
                // 表单提交路径,及指定提交的用户名和密码
                .loginProcessingUrl("/login")
                .usernameParameter("username")
                .passwordParameter("password")
                // 登录成功后的跳转路径
                .successForwardUrl("/")
                //登录成功或者失败的处理器
                .successHandler(authenticationService)
                .failureHandler(authenticationService)

                //指定退出的相关信息
                .and().logout()
                //指定退出的地址
                .logoutUrl("/logout")
                .logoutSuccessHandler(authenticationService)

                .and()
                //记住我功能打开
                //如果直接配制，则是将md5(数据+过期时间+密码直接)直接保存在本地cookie中，安全性不好，
                .rememberMe()
                .tokenRepository(getPersistentTokenRepository())
                //默认的失效时间是2周
                .tokenValiditySeconds(1209600)
                //自定义静音记住我的的值
                .rememberMeParameter("remember-me")
                .and()
                //权限设置
                .authorizeRequests()
                // 放行登录页面
                .antMatchers("/toLoginPage")
                .permitAll()
                .anyRequest()
                .authenticated();


        // 在默认的过滤器链的配制中，CSRF功能是打开，测试时，先关闭
        http.csrf().disable();
        // 在SpringSecurity在默认情况下是拒绝iframe的加载请求，需要先打开
        // sameOrigin();可以加载同源域名下的文件
        http.headers().frameOptions().sameOrigin();
    }

    @Autowired
    private DataSource dataSource;

    @Bean
    public PersistentTokenRepository getPersistentTokenRepository() {
        JdbcTokenRepositoryImpl tokenRepository = new JdbcTokenRepositoryImpl();

        tokenRepository.setDataSource(dataSource);
        //首次执行创建表的操作
        //tokenRepository.setCreateTableOnStartup(true);

        return tokenRepository;
    }
}
