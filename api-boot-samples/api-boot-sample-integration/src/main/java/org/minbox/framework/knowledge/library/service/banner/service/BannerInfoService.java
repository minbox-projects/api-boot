package org.minbox.framework.knowledge.library.service.banner.service;

import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceField;
import org.minbox.framework.api.boot.plugin.resource.load.annotation.ResourceLoad;
import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.constants.Constant;
import org.minbox.framework.knowledge.library.common.dsl.DBannerInfoEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.minbox.framework.knowledge.library.service.banner.dto.BannerInfoDTO;
import org.minbox.framework.knowledge.library.service.constants.ResourceType;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.sql.Timestamp;
import java.util.List;

/**
 * 轮播图业务逻辑
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-10 10:43
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BannerInfoService extends BaseService {
    /**
     * 查询全部轮播图
     *
     * @return
     * @throws KnowledgeException
     */
    @ResourceLoad
    @ResourceField(name = "bannerImage", source = "biId", type = ResourceType.BANNER)
    public List<BannerInfoDTO> selectAllBanner() throws KnowledgeException {
        // 轮播图动态查询实体
        DBannerInfoEntity dBannerInfoEntity = DBannerInfoEntity.DSL();
        // 当前时间
        Timestamp currentTime = new Timestamp(System.currentTimeMillis());
        return dslQuery.createSearchable()
                .selectFrom(dBannerInfoEntity)
                .where(dBannerInfoEntity.biStatus.eq(Constant.OK))
                .and(dBannerInfoEntity.biStartTime.lt(currentTime))
                .and(dBannerInfoEntity.biEndTime.gt(currentTime))
                .resultType(BannerInfoDTO.class)
                .fetch();
    }
}
