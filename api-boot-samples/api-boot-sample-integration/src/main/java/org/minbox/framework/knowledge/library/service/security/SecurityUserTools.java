package org.minbox.framework.knowledge.library.service.security;

import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.ObjectUtils;

/**
 * 安全用户工具类
 *
 * @author：于起宇 <p>
 * ================================
 * Created with IDEA.
 * Date：2018/11/14
 * Time：4:20 PM
 * 个人博客：http://blog.yuqiyu.com
 * 简书：http://www.jianshu.com/u/092df3f77bca
 * 码云：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 * ================================
 * </p>
 */
public class SecurityUserTools {
    static final String ANONYMOUS_USER = "anonymousUser";

    /**
     * 获取登录的当前对象信息
     *
     * @return
     */
    public static KnowledgeLibraryUserDetails getCurrentUser() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        if (ObjectUtils.isEmpty(principal) || ANONYMOUS_USER.equals(principal)) {
            throw new KnowledgeException("还未登录哟.");
        }
        return (KnowledgeLibraryUserDetails) principal;
    }
}
