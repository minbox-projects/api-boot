package org.minbox.framework.knowledge.library.service.resource.store;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.resource.load.ApiBootResourceStoreDelegate;
import org.minbox.framework.knowledge.library.service.resource.service.CommonResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * ApiBoot Resource Load 资源查询
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-16 13:44
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
public class KnowledgeResourceService implements ApiBootResourceStoreDelegate {
    /**
     * 资源加载
     */
    @Autowired
    private CommonResourceService commonResourceService;

    /**
     * 加载资源路径
     *
     * @param targetId 业务编号
     * @param type     资源类型
     * @return
     * @throws ApiBootException
     */
    @Override
    public List<String> loadResourceUrl(String targetId, String type) throws ApiBootException {
        return commonResourceService.selectByType(targetId, type);
    }

    @Override
    public void addResource(String sourceFieldValue, String resourceType, List<String> resourceUrls) throws ApiBootException {

    }

    @Override
    public void deleteResource(String sourceFieldValue, String resourceType) throws ApiBootException {

    }

    @Override
    public void updateResource(String sourceFieldValue, String resourceType, List<String> resourceUrls) throws ApiBootException {

    }
}
