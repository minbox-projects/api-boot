package org.minbox.framework.api.boot.autoconfigure.security;

import org.minbox.framework.api.boot.plugin.security.ApiBootWebSecurityConfiguration;
import org.minbox.framework.api.boot.plugin.security.SecurityUser;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.security.SecurityProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.util.ObjectUtils;

import java.util.List;

import static org.minbox.framework.api.boot.autoconfigure.security.ApiBootSecurityProperties.API_BOOT_SECURITY_PREFIX;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-25 15:04
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
@EnableConfigurationProperties(ApiBootSecurityProperties.class)
@ConditionalOnClass(ApiBootWebSecurityConfiguration.class)
@ConditionalOnProperty(prefix = API_BOOT_SECURITY_PREFIX, name = "away", havingValue = "memory", matchIfMissing = true)
@Order(SecurityProperties.ACCESS_OVERRIDE_ORDER)
public class ApiBootWebSecurityMemoryAutoConfiguration extends ApiBootWebSecurityAutoConfiguration {

    public ApiBootWebSecurityMemoryAutoConfiguration(ApiBootSecurityProperties apiBootSecurityProperties) {
        super(apiBootSecurityProperties);
    }

    @Override
    @Bean
    protected UserDetailsService userDetailsService() {
        InMemoryUserDetailsManager memoryUserDetails = new InMemoryUserDetailsManager();
        List<SecurityUser> users = apiBootSecurityProperties.getUsers();
        if (!ObjectUtils.isEmpty(users)) {
            for (SecurityUser securityUser : users) {
                memoryUserDetails.createUser(User.builder().username(securityUser.getUsername()).password(passwordEncoder().encode(securityUser.getPassword())).roles(securityUser.getRoles()).build());
            }
        }
        return memoryUserDetails;
    }

}
