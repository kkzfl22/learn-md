package com.nullnull.config;

import com.nullnull.handler.MyAccessDeniedHandler;
import com.nullnull.service.impl.MyAuthenticationService;
import com.nullnull.service.impl.MyUserDetailService;
import com.nullnull.service.impl.ValidateCodeFilter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.rememberme.JdbcTokenRepositoryImpl;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;

import javax.sql.DataSource;

/**
 * security的配制
 *
 * <p>可以认为 HttpSecurity 是 WebSecurity 的一部分， WebSecurity 是包含 HttpSecurity 的更大 的一个概念
 *
 * <p>@EnableGlobalMethodSecurity 是开启方法注解的标识</p>
 *
 * @author liujun
 * @since 2023/6/7
 */
@Configuration
@EnableGlobalMethodSecurity(prePostEnabled = true)
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
     * 自定义权限不足的信息
     */
    @Autowired
    private MyAccessDeniedHandler accessDeniedHandler;


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
                //用于指定Logout的配制，否则在CSRF防护时，无法做退出
                .logoutRequestMatcher(new AntPathRequestMatcher("/logout", "GET"))
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
                .antMatchers("/toLoginPage", "/logout", "/toLoginPage")
                .permitAll();
        //当配制权限后，此需要注释
        //.anyRequest()
        //.authenticated();


        // 在SpringSecurity在默认情况下是拒绝iframe的加载请求，需要先打开
        // sameOrigin();可以加载同源域名下的文件
        http.headers().frameOptions().sameOrigin();

        //配制会话超时后的地址
        http.sessionManagement()
                //session无效后跳转地址，默认的登录页
                .invalidSessionUrl("/toLoginPage")
                //设置session的最大会话数量为1，也就是同一时间只能一个用户在线，会产生互踢的效果
                .maximumSessions(2)
                //阻止用户二次登录,当到达maximumSessions设置的最大会话个数时，阻止登录
                .maxSessionsPreventsLogin(true)
                //设置Session过期后的跳转地地址
                .expiredUrl("/toLoginPage");


        // 在默认的过滤器链的配制中，CSRF功能是打开，测试时，先关闭
        //http.csrf().disable();
        //设置CSRF防护,设置哪些不需要防护,默认CSRF是打开的
        http.csrf().ignoringAntMatchers("/user/saveOrUpdate", "/product/saveOrUpdate");


        //设置允许跨域
        http.cors().configurationSource(corsConfigurationSource());

        //配制基于URL的安全表达式
        //用户管理模块，需要使用ADMIN才能访问
        //  http.authorizeRequests().antMatchers("/user/**").hasRole("ADMIN");
        //http.authorizeRequests().antMatchers("/user/**")
        //        //使用自定义的方法，进行验证
        //        .access("@myAuthorizationService.check(authentication,request)");
        http.authorizeRequests().antMatchers("/user/delete/{id}")
                //自定义Bean授权，并携带路径参数
                .access("@myAuthorizationService.numCheck(authentication,request,#id)");

        //商品模块,需要指定的角色，并且IP为本机
        http.authorizeRequests().antMatchers("/product/**")
                .access("hasAnyRole('ADMIN,PRODUCT') and hasIpAddress('127.0.0.1')");
        //自定义权限不足的信息
        http.exceptionHandling().accessDeniedHandler(accessDeniedHandler);


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


    /**
     * 跨域的配置信息源
     *
     * @return 跨域的配制
     */
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        //设置允许跨域的站点
        corsConfiguration.addAllowedOrigin("*");
        //设置允许跨域的http方法
        corsConfiguration.addAllowedMethod("*");
        //设置允许跨域的请求头
        corsConfiguration.addAllowedHeader("*");
        //允许带凭证
        corsConfiguration.setAllowCredentials(true);

        //配制对所有的URL生产
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", corsConfiguration);

        return source;
    }
}
