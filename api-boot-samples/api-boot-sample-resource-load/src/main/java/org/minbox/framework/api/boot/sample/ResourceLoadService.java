/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.api.boot.sample;

import org.minbox.framework.api.boot.common.exception.ApiBootException;
import org.minbox.framework.api.boot.plugin.resource.load.ApiBootResourceStoreDelegate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * 资源加载数据代理实现类
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-15 13:54
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Service
public class ResourceLoadService implements ApiBootResourceStoreDelegate {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ResourceLoadService.class);

    @Override
    public List<String> loadResourceUrl(String sourceFieldValue, String resourceType) throws ApiBootException {
        logger.info("查询资源的业务逻辑字段值：{}", sourceFieldValue);
        logger.info("资源类型：{}", resourceType);
        return Arrays.asList(new String[]{"http://test.oss.com/111.png"});
    }

    @Override
    public void addResource(String sourceFieldValue, String resourceType, List<String> resourceUrls) throws ApiBootException {
        logger.info("添加资源，业务逻辑编号：{}，资源类型：{}，资源路径集合：{}", sourceFieldValue, resourceType, resourceUrls);
    }

    @Override
    public void deleteResource(String sourceFieldValue, String resourceType) throws ApiBootException {
        logger.info("删除资源，业务逻辑编号：{}，资源类型：{}", sourceFieldValue, resourceType);
    }

    @Override
    public void updateResource(String sourceFieldValue, String resourceType, List<String> resourceUrls) throws ApiBootException {
        logger.info("更新资源，业务逻辑编号：{}，资源类型：{}，资源路径集合：{}", sourceFieldValue, resourceType, resourceUrls);
    }
}
