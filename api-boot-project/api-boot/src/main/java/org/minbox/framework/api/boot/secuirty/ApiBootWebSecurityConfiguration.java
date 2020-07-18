package org.minbox.framework.api.boot.secuirty;

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
 * ApiBoot integrates SpringSecurity configuration class
 *
 * @author 恒宇少年
 */
public abstract class ApiBootWebSecurityConfiguration extends WebSecurityConfigurerAdapter {
    /**
     * Configure Excluded Path List
     *
     * @return Path list
     */
    protected List<String> configureIgnoreUrls() {
        return Collections.emptyList();
    }

    /**
     * Configure SpringSecurity Web
     * <p>
     * Set a list of paths to exclude security intercepts
     * </p>
     *
     * @param web {@link WebSecurity}
     */
    @Override
    public void configure(WebSecurity web) {
        WebSecurity.IgnoredRequestConfigurer ignoredRequestConfigurer = web.ignoring();
        configureIgnoreUrls().forEach(url -> ignoredRequestConfigurer.antMatchers(url));
    }

    /**
     * Configure user authentication management
     * <p>
     * Password encryption method {@link #passwordEncoder()}
     * </p>
     *
     * @param auth {@link AuthenticationManagerBuilder}
     * @throws Exception exception
     */
    @Override
    protected void configure(AuthenticationManagerBuilder auth) throws Exception {
        auth.userDetailsService(userDetailsService()).passwordEncoder(passwordEncoder());
    }

    /**
     * Authorized manager
     *
     * @return {@link AuthenticationManager}
     * @throws Exception exception
     */
    @Override
    @Bean
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    /**
     * Disable basic http
     *
     * @param http {@link HttpSecurity}
     * @throws Exception exception
     */
    @Override
    protected void configure(HttpSecurity http) throws Exception {
        if (disableHttpBasic()) {
            http.httpBasic().disable();
        }
        if (disableCsrf()) {
            http.csrf().disable();
        }
        http.exceptionHandling().accessDeniedHandler(getAccessDeniedHandler());
        http.exceptionHandling().authenticationEntryPoint(getAuthenticationEntryPoint());
    }

    /**
     * Password encryption method
     *
     * @return {@link BCryptPasswordEncoder}
     * @see PasswordEncoder
     */
    @Bean
    @ConditionalOnMissingBean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    /**
     * Get spring security exception handler
     * <p>
     * This method is left to the implementation class to obtain the customized {@link AccessDeniedHandler} implementation class IOC instance from the project
     * If the implementation class does not return an instance,
     * the default {@link org.minbox.framework.api.boot.plugin.security.handler.ApiBootDefaultAccessDeniedHandler} is used to return
     * </p>
     *
     * @return {@link AccessDeniedHandler}
     */
    protected abstract AccessDeniedHandler getAccessDeniedHandler();

    /**
     * Get authentication endpoint processing
     *
     * @return {@link AuthenticationEntryPoint}
     */
    protected abstract AuthenticationEntryPoint getAuthenticationEntryPoint();

    /**
     * Disable basic http
     * <p>
     * This method is an abstract method, and the logic is implemented by subclasses
     * </p>
     *
     * @return Disable HttpBasic or not
     * @see ApiBootWebSecurityConfiguration#configure(HttpSecurity)
     */
    protected abstract boolean disableHttpBasic();

    /**
     * Disable csrf
     * <p>
     * This method is an abstract method, and the logic is implemented by subclasses
     * </p>
     *
     * @return Disable Csrf or not
     */
    protected abstract boolean disableCsrf();
}
