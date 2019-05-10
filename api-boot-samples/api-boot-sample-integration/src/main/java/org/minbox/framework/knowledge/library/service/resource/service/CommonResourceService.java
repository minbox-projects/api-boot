package org.minbox.framework.knowledge.library.service.resource.service;

import com.gitee.hengboy.mybatis.enhance.sort.SortEnum;
import org.minbox.framework.knowledge.library.common.base.BaseService;
import org.minbox.framework.knowledge.library.common.dsl.DCommonResourceEntity;
import org.minbox.framework.knowledge.library.common.dsl.DCommonResourceTypeEntity;
import org.minbox.framework.knowledge.library.common.exception.KnowledgeException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 资源业务逻辑
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-16 13:46
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommonResourceService extends BaseService {
    /**
     * 根据目标编号 & 资源类型
     * 查询资源列表
     *
     * @param targetId 目标编号
     * @param type     资源类型
     * @return
     * @throws KnowledgeException
     */
    public List<String> selectByType(String targetId, String type) throws KnowledgeException {
        DCommonResourceEntity dCommonResourceEntity = DCommonResourceEntity.DSL();
        DCommonResourceTypeEntity dCommonResourceTypeEntity = DCommonResourceTypeEntity.DSL();
        return dslQuery.createSearchable()
                .select(dCommonResourceEntity.crUrl)
                .from(dCommonResourceEntity)
                .leftJoin(dCommonResourceEntity.crTypeId, dCommonResourceTypeEntity.crtId)
                .where(dCommonResourceTypeEntity.crtFlag.eq(type))
                .and(dCommonResourceEntity.crTargetId.eq(targetId))
                .orderBy(dCommonResourceEntity.crCreateTime, SortEnum.DESC)
                .resultType(String.class)
                .fetch();
    }
}
