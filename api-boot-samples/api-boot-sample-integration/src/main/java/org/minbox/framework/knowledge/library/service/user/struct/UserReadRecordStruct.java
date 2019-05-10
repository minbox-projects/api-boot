package org.minbox.framework.knowledge.library.service.user.struct;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Mappings;
import org.mapstruct.factory.Mappers;
import org.minbox.framework.knowledge.library.common.entity.UserReadRecordEntity;

/**
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-22 09:37
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Mapper
public interface UserReadRecordStruct {
    /**
     * get new mapStruct instance
     */
    UserReadRecordStruct INSTANCE = Mappers.getMapper(UserReadRecordStruct.class);

    /**
     * 转换阅读记录实体
     *
     * @param userId    用户编号
     * @param articleId 阅读记录编号
     * @return
     */
    @Mappings({
            @Mapping(source = "userId", target = "urrUserId"),
            @Mapping(source = "articleId", target = "urrArticleId")
    })
    UserReadRecordEntity from(String userId, String articleId);
}
