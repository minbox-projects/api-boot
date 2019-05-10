package org.minbox.framework.knowledge.library.service.user.service;

import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.constants.Constant;
import org.minbox.framework.knowledge.library.common.dsl.DUserInfoEntity;
import org.minbox.framework.knowledge.library.common.entity.UserInfoEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.user.mapper.UserInfoMapper;
import org.minbox.framework.knowledge.library.service.user.struct.UserStruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;

/**
 * 用户信息业务逻辑
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-09 13:47
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class UserInfoService extends BaseService {
    /**
     * 密码加密
     */
    @Autowired
    private PasswordEncoder passwordEncoder;
    /**
     * 用户数据接口
     */
    @Autowired
    private UserInfoMapper userInfoMapper;

    /**
     * 根据openId查询用户信息
     *
     * @param openId openId
     * @return UserInfoEntity
     */
    public UserInfoEntity loadByOpenId(String openId) {
        DUserInfoEntity dUserInfoEntity = DUserInfoEntity.DSL();
        return dslQuery.createSearchable().selectFrom(dUserInfoEntity).where(dUserInfoEntity.uiOpenId.eq(openId)).and(dUserInfoEntity.uiStatus.eq(Constant.OK)).and(dUserInfoEntity.uiIsLock.eq(Constant.NO)).resultType(UserInfoEntity.class).fetchOne();
    }

    /**
     * 用户注册
     * 1、验证是否已经注册过
     * 2、执行用户信息保存
     *
     * @param nickName 昵称
     * @param openId   openId
     * @return
     * @throws KnowledgeException
     */
    public void register(String nickName, String openId) throws KnowledgeException {
        UserInfoEntity userInfoEntity = loadByOpenId(openId);
        if (!ObjectUtils.isEmpty(userInfoEntity)) {
            throw new KnowledgeException("不需要重复注册哟.");
        }
        // 最后登录时间
        Timestamp lastLoginTime = new Timestamp(System.currentTimeMillis());
        // 转换新用户信息
        userInfoEntity = UserStruct.INSTANCE.toRegisterUser(nickName, openId, passwordEncoder.encode(openId), lastLoginTime);
        
        // 执行保存用户信息
        userInfoMapper.insert(userInfoEntity);
    }
}
