package org.minbox.framework.api.boot.sample;

import org.minbox.framework.security.delegate.SecurityStoreDelegate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.util.ObjectUtils;

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
 * GitHub：https://github.com/hengboy
 */
@Component
public class DisableDefaultUserTableStoreDelegate implements SecurityStoreDelegate {

    @Autowired
    private SystemUserMapper systemUserMapper;

    /**
     * 根据用户名查询用户信息
     *
     * @param username 用户名
     * @return UserDetails
     * @throws UsernameNotFoundException 用户不存在
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SystemUser user = systemUserMapper.findByUserName(username);
        if (ObjectUtils.isEmpty(user)) {
            throw new UsernameNotFoundException("用户：" + username + "不存在");
        }
        return new DisableDefaultUserDetails(user.getUserName(), user.getPassword());
    }
}
