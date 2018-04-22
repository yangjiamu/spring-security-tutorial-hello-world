package com.study.springboot.springsecuritytutorialhelloworld.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .authorizeRequests()
                .antMatchers("/css/**", "/js/**", "/fonts/**", "/index").permitAll()  // 都可以访问
                .antMatchers("/users/**").hasRole("USER")   // 需要相应的角色才能访问
                .antMatchers("/admins/**").hasRole("ADMIN")   // 需要相应的角色才能访问
                .and()
                .formLogin()   //基于 Form 表单登录验证
                .loginPage("/login").failureUrl("/login-error") // 自定义登录界面
                .and()
                .exceptionHandling().accessDeniedPage("/403"); // 处理异常，拒绝访问就重定向到 403 页面
        http.logout().logoutSuccessUrl("/");   // 成功登出后，重定向到 首页
    }

    @Bean
    public UserDetailsService userDetailsService(){
        InMemoryUserDetailsManager manager = new InMemoryUserDetailsManager();

        manager.createUser(User.withUsername("yang").password("123456").roles("USER").build());
        manager.createUser(User.withUsername("admin").password("123456").roles("USER", "ADMIN").build());
        return manager;
    }

    /*@Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        super.configure(auth);
    }*/

    @Autowired
    public void configGlobal(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService());
    }
}