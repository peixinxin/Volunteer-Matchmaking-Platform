package com.example.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.example.demo.service.CustomUserDetailsService;
/* 
@Configuration // 标记这个类是一个Spring配置类
public class SecurityConfig {

    // 定义一个返回UserDetailsService实现的Bean，用于处理用户详细信息的服务
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    // 定义一个返回PasswordEncoder实现的Bean，用于密码加密
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    // 定义一个返回DaoAuthenticationProvider实现的Bean，用于处理身份验证
    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService()); // 设置UserDetailsService
        authProvider.setPasswordEncoder(passwordEncoder()); // 设置PasswordEncoder
        return authProvider;
    }

    // 配置Spring Security的HttpSecurity
    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
            .authenticationProvider(authenticationProvider()) // 设置身份验证提供者
            .authorizeHttpRequests(authorize -> authorize
                // 设置路径匹配器，允许对特定路径的请求
                .requestMatchers(HttpMethod.POST, "/api/register").permitAll() // 允许所有用户访问注册端点
                .requestMatchers(HttpMethod.POST, "/api/login").permitAll() // 允许所有用户访问登录端点
                .anyRequest().authenticated()) // 其他所有请求都需要身份验证
            .formLogin(formLogin -> formLogin
                .loginPage("/login") // 设置自定义登录页面的路径
                .usernameParameter("email") // 设置登录表单中用户名字段的参数名
                .defaultSuccessUrl("/userhomePage", true) // 登录成功后重定向到的路径
                .permitAll()) // 允许所有用户访问登录页面
            .logout(logout -> logout
                .logoutUrl("/api/user/logout") // 设置自定义注销路径
                .logoutSuccessUrl("/login") // 注销成功后重定向到的路径
                .invalidateHttpSession(true) // 注销时使HttpSession失效
                .deleteCookies("JSESSIONID") // 注销时删除指定的cookie
                .permitAll()); // 允许所有用户访问注销路径
        
        http.csrf(csrf -> csrf.disable()); // 禁用CSRF保护
        http.cors(cors -> cors.disable()); // 禁用CORS保护
        return http.build(); // 构建并返回SecurityFilterChain
    }
}

*/



@Configuration
public class SecurityConfig {
    @Bean
    public UserDetailsService userDetailsService() {
        return new CustomUserDetailsService();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public DaoAuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(authorize -> authorize
                        .requestMatchers("/volunteer").permitAll()
                        .requestMatchers("/organization").permitAll()
                        .requestMatchers("/supervisor").permitAll()
                        .requestMatchers(HttpMethod.POST, "/login").permitAll()
                        .requestMatchers(HttpMethod.POST, "/register").permitAll()
                        .requestMatchers(HttpMethod.POST, "/api/register").permitAll() // new
                        .requestMatchers(HttpMethod.POST, "/api/login").permitAll() // new
                        .anyRequest().permitAll())  // 其他所有请求都需要身份验证
                .formLogin(formLogin -> formLogin
                        .loginPage("/login")
                        .usernameParameter("email")
                        .defaultSuccessUrl("/userhome", true) // modified
                        .permitAll())
                .logout(logout -> logout
                        .logoutUrl("/api/user/logout")
                        .logoutSuccessUrl("/login")
                        .invalidateHttpSession(true)
                        .deleteCookies("JSESSIONID")
                        .permitAll());
        
        http.csrf(csrf -> csrf.disable());
        http.cors(cors -> cors.disable());
        return http.build();
    }
}

