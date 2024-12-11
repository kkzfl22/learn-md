package org.example.webflux.config;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
import org.springframework.security.core.userdetails.ReactiveUserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.server.SecurityWebFilterChain;

/**
 * 认证的配制
 *
 * @author nullnull
 * @since 2024/12/11
 */
@SpringBootApplication
//该注解导入所需的配制，以启用特定的带注解methodInterceptor
@EnableReactiveMethodSecurity
public class SecurityConfiguration {

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                //配制基于表单的认证
                .formLogin()
                //继续往下执行
                .and()
                //配制授权
                .authorizeExchange()
                //禁用授权
                .anyExchange()
                //需要有认证的用户
                .authenticated()
                //继续向下配制
                .and()
                .build()
                ;
    }

    @Bean
    public ReactiveUserDetailsService userDetailsService() {
        User.UserBuilder userBuilder = User.withDefaultPasswordEncoder();
        UserDetails user1 = userBuilder.username("nullnull")
                .password("123456")
                .roles("admin")
                .build();

        UserDetails user2 = userBuilder.username("lisi")
                .password("123456")
                .roles("none")
                .build();
        return new MapReactiveUserDetailsService(user1,user2);
    }


}
