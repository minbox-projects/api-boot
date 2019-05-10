package org.minbox.framework.knowledge.library.service.security;

import lombok.AllArgsConstructor;
import lombok.Getter;
import org.minbox.framework.knowledge.library.common.constants.Constant;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;
import java.util.Collection;

/**
 * 自定义读取用户的实体类
 * 完成自定义读取SpringSecurity所需用户
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-09 08:57
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@AllArgsConstructor
public class KnowledgeLibraryUserDetails implements UserDetails {
    /**
     * 用户基本信息
     */
    @Getter
    private UserInfoEntity user;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return new ArrayList() {
            {
                add((GrantedAuthority) () -> "ROLE_USER");
            }
        };
    }

    @Override
    public String getPassword() {
        return user.getUiPassword();
    }

    @Override
    public String getUsername() {
        return user.getUiOpenId();
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return Constant.NO.equals(user.getUiIsLock());
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return Constant.OK.equals(user.getUiStatus());
    }
}
