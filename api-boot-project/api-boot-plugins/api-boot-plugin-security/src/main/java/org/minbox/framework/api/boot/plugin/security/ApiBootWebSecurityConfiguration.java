package org.minbox.framework.api.boot.plugin.security;

import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Collections;
import java.util.List;

/**
 * ApiBoot 整合SpringSecurity Web Security 配置类
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-25 17:58
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
public class ApiBootWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * 配置排除的路径列表
     *
     * @return 排除路径列表
     */
    protected List<String> configureIgnoreUrls() {
        return Collections.emptyList();
    }

    /**
     * 排除安全拦截swagger、actuator等路径
     *
     * @param web web安全构建对象
     * @throws Exception 异常信息
     */
    @Override
    public void configure(WebSecurity web) throws Exception {
        WebSecurity.IgnoredRequestConfigurer ignoredRequestConfigurer = web.ignoring();
        configureIgnoreUrls().stream().forEach(url -> ignoredRequestConfigurer.antMatchers(url));
    }

    /**
     * 配置用户认证管理
     * 1. 设置用户业务逻辑实例
     * 2. 设置用户密码加密方式为BCrypt
     *
     * @param auth 认证管理者构建对象
     * @throws Exception 异常信息
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * 设置授权管理者
     * 用于整合oauth2的password授权模式
     *
     * @return AuthenticationManager
     * @throws Exception 异常信息
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * 禁用http basic
     *
     * @param http http安全构建对象
     * @throws Exception 异常信息
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.httpBasic().disable();
    }

    /**
     * 用户登录或者获取Token时的密码加密方式
     *
     * @return BCryptPasswordEncoder
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}
