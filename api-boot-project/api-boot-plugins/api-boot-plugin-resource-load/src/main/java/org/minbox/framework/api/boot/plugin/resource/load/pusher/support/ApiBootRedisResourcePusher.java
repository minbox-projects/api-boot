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

package org.minbox.framework.api.boot.plugin.resource.load.pusher.support;

import org.minbox.framework.api.boot.plugin.resource.load.ApiBootResourceStoreDelegate;
import org.minbox.framework.api.boot.plugin.resource.load.context.ApiBootResourceContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.util.ObjectUtils;

import java.lang.reflect.Method;
import java.util.Collections;
import java.util.List;

/**
 * ApiBoot Redis Resource Pusher
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-19 09:34
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootRedisResourcePusher extends ApiBootJdbcResourcePusher {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootRedisResourcePusher.class);

    /**
     * Redis Template
     */
    private RedisTemplate redisTemplate;

    public ApiBootRedisResourcePusher(ApiBootResourceStoreDelegate apiBootResourceStoreDelegate, RedisTemplate redisTemplate) {
        super(apiBootResourceStoreDelegate);
        this.redisTemplate = redisTemplate;
    }

    /**
     * Check whether the data is cached in redis
     * If not cached, query from the database
     * If cached , query from the redis
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @return resource urls
     */
    @Override
    public List<String> loadResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType) {
        if (ObjectUtils.isEmpty(sourceFieldValue) || ObjectUtils.isEmpty(resourceType)) {
            logger.warn("@ResourceField param [source]|[type]|[name] have empty value, load resource urls with redis fail.");
            return Collections.emptyList();
        }
        // formatter redis key
        String resourceRedisKey = ApiBootResourceContext.formatterCacheKey(declaredMethod, sourceFieldValue, resourceType);
        // get resource size
        long resourceSize = redisTemplate.opsForList().size(resourceRedisKey);
        if (resourceSize <= 0) {
            // load resource url from jdbc
            List<String> resourceUrls = super.loadResourceUrl(declaredMethod, sourceFieldValue, resourceType);
            // push resource urls to redis
            redisTemplate.opsForList().rightPushAll(resourceRedisKey, resourceUrls);
        }
        // pull all resource urls from redis
        return redisTemplate.opsForList().range(resourceRedisKey, 0, -1);
    }

    /**
     * delete resource from redis
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     */
    @Override
    public void deleteResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType) {
        if (ObjectUtils.isEmpty(sourceFieldValue) || ObjectUtils.isEmpty(resourceType)) {
            logger.warn("@ResourceField param [source]|[type]|[name] have empty value, delete resource urls with redis fail.");
            return;
        }
        // formatter redis key
        String resourceRedisKey = ApiBootResourceContext.formatterCacheKey(declaredMethod, sourceFieldValue, resourceType);

        // execute delete redis resource
        redisTemplate.delete(resourceRedisKey);

        // delete from jdbc
        super.deleteResourceUrl(declaredMethod, sourceFieldValue, resourceType);
    }

    /**
     * insert into redis
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @param resourceUrls     resource urls
     */
    @Override
    public void insertResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType, List<String> resourceUrls) {
        if (ObjectUtils.isEmpty(sourceFieldValue) || ObjectUtils.isEmpty(resourceType) || ObjectUtils.isEmpty(resourceUrls)) {
            logger.warn("@ResourceField param [source]|[type]|[name] have empty value, insert resource urls with redis fail.");
            return;
        }
        super.insertResourceUrl(declaredMethod, sourceFieldValue, resourceType, resourceUrls);
        // formatter redis key
        String resourceRedisKey = ApiBootResourceContext.formatterCacheKey(declaredMethod, sourceFieldValue, resourceType);
        // push resource urls to redis
        redisTemplate.opsForList().rightPushAll(resourceRedisKey, resourceUrls);
    }

    /**
     * update redis resource data
     *
     * @param declaredMethod   declared method
     * @param sourceFieldValue sourceFieldValue
     * @param resourceType     resourceType
     * @param resourceUrls     resource urls
     */
    @Override
    public void updateResourceUrl(Method declaredMethod, String sourceFieldValue, String resourceType, List<String> resourceUrls) {
        if (ObjectUtils.isEmpty(sourceFieldValue) || ObjectUtils.isEmpty(resourceType) || ObjectUtils.isEmpty(resourceUrls)) {
            logger.warn("@ResourceField param [source]|[type]|[name] have empty value, update resource urls with redis fail.");
            return;
        }
        super.updateResourceUrl(declaredMethod, sourceFieldValue, resourceType, resourceUrls);

        // execute delete redis data
        deleteResourceUrl(declaredMethod, sourceFieldValue, resourceType);

        // execute insert redis data
        insertResourceUrl(declaredMethod, sourceFieldValue, resourceType, resourceUrls);
    }
}
