package org.minbox.framework.knowledge.library.service.user.struct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;

import java.sql.Timestamp;

/**
 * 用户MapStruct定义转换接口
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-11 15:54
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Mapper
public interface UserStruct {
    /**
     * get new mapStruct instance
     */
    UserStruct INSTANCE = Mappers.getMapper(UserStruct.class);

    /**
     * 转换注册用户实体
     *
     * @param uiNickName      用户昵称
     * @param uiOpenId        用户OpenId
     * @param uiPassword      用户密码
     * @param uiLastLoginTime 最后登录时间
     * @return
     */
    UserInfoEntity toRegisterUser(String uiNickName, String uiOpenId, String uiPassword, Timestamp uiLastLoginTime);

}
