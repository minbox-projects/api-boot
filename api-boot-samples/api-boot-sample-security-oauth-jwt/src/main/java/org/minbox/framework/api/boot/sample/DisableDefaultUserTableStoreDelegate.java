package org.minbox.framework.api.boot.sample;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.minbox.framework.api.boot.plugin.security.delegate.ApiBootStoreDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * 禁用默认的用户表结构
 * 使用自定义数据源读取用户信息
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-20 15:12
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@Component
public class DisableDefaultUserTableStoreDelegate implements ApiBootStoreDelegate {

    @Autowired
    private PasswordEncoder passwordEncoder;

    /**
     * 用户列表示例
     * 从该集合内读取用户信息
     * 可以使用集合内的用户获取access_token
     */
    static List<String> users = new ArrayList() {
        {
            add("api-boot");
            add("hengboy");
            add("yuqiyu");
        }
    };

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return
     * @throws UsernameNotFoundException
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (!users.contains(username)) {
            throw new UsernameNotFoundException("用户：" + username + "不存在");
        }
        return new DisableDefaultUserDetails(username);
    }

    @Data
    @AllArgsConstructor
    @NoArgsConstructor
    class DisableDefaultUserDetails implements UserDetails {
        private String username;

        @Override
        public Collection<? extends GrantedAuthority> getAuthorities() {
            return new ArrayList() {
                {
                    add((GrantedAuthority) () -> "ROLE_USER");
                }
            };
        }

        /**
         * 示例密码使用123456
         *
         * @return
         */
        @Override
        public String getPassword() {
            return passwordEncoder.encode("123456");
        }

        @Override
        public String getUsername() {
            return username;
        }

        @Override
        public boolean isAccountNonExpired() {
            return true;
        }

        @Override
        public boolean isAccountNonLocked() {
            return true;
        }

        @Override
        public boolean isCredentialsNonExpired() {
            return true;
        }

        @Override
        public boolean isEnabled() {
            return true;
        }
    }
}
