package org.minbox.framework.knowledge.library.service.security;

import org.minbox.framework.api.boot.plugin.security.delegate.ApiBootStoreDelegate;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;
import org.minbox.framework.knowledge.library.service.user.service.UserInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

/**
 * 自定义查询用户
 * 根据微信用户openId
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-09 08:53
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
public class KnowledgeLibraryUserDelegate implements ApiBootStoreDelegate {
    /**
     * 用户业务逻辑注入
     */
    @Autowired
    private UserInfoService userInfoService;

    /**
     * username == openId
     * 根据openId查询出用户基本信息
     *
     * @param username 用户微信openId
     * @return UserDetails Instance
     * @throws UsernameNotFoundException 用户并未找到
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserInfoEntity userInfoEntity = userInfoService.loadByOpenId(username);
        if (ObjectUtils.isEmpty(userInfoEntity)) {
            throw new UsernameNotFoundException("用户：[" + username + "]，不存在");
        }
        return new KnowledgeLibraryUserDetails(userInfoEntity);
    }
}
