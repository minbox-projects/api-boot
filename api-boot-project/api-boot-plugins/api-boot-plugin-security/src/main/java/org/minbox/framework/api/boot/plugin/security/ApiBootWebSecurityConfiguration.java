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
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;

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
 * GitHub：https://github.com/hengboy
 */
public abstract class ApiBootWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
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
        if (disableHttpBasic()) {
            http.httpBasic().disable();
        }
        if (disableCsrf()) {
            http.csrf().disable();
        }
        // 异常处理器配置
        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
        http.exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint());
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

    /**
     * 获取Spring Security 异常处理器
     * 该方法留给实现类实现，实现类从项目内获取自定义的AccessDeniedHandler实现类IOC实例
     * 如果实现类不返回实例则使用默认的ApiBootDefaultAccessDeniedHandler进行返回
     *
     * @return AccessDeniedHandler
     */
    protected abstract AccessDeniedHandler getAccessDeniedHandler();

    /**
     * 获取认证端点处理
     *
     * @return AuthenticationEntryPoint
     */
    protected abstract AuthenticationEntryPoint getAuthenticationEntryPoint();

    /**
     * 查询是否禁用http basic
     *
     * @return true：禁用，false：不禁用
     */
    protected abstract boolean disableHttpBasic();

    /**
     * 查询是否禁用csrf
     *
     * @return true：禁用，false：不禁用
     */
    protected abstract boolean disableCsrf();
}
