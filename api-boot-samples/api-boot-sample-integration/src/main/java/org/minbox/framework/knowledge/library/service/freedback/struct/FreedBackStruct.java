package org.minbox.framework.knowledge.library.service.freedback.struct;

import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.minbox.framework.knowledge.library.common.entity.FeedbackEntity;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-22 11:22
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Mapper
public interface FreedBackStruct {
    /**
     * get new mapStruct instance
     */
    FreedBackStruct INSTANCE = Mappers.getMapper(FreedBackStruct.class);

    /**
     * 转换意见反馈实体
     *
     * @param kfUserId  用户编号
     * @param kfContent 意见反馈内容
     * @return
     */
    FeedbackEntity from(String kfUserId, String kfContent);
}
