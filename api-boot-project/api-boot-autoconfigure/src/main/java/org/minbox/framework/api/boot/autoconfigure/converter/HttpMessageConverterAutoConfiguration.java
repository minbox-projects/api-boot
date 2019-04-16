/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *    Licensed under the Apache License, Version 2.0 (the "License");
 *    you may not use this file except in compliance with the License.
 *    You may obtain a copy of the License at
 *
 *        http://www.apache.org/licenses/LICENSE-2.0
 *
 *    Unless required by applicable law or agreed to in writing, software
 *    distributed under the License is distributed on an "AS IS" BASIS,
 *    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *    See the License for the specific language governing permissions and
 *    limitations under the License.
 */

package org.minbox.framework.api.boot.autoconfigure.converter;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.serializer.ValueFilter;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import org.minbox.framework.api.boot.common.tools.ClassTools;
import org.reflections.util.ConfigurationBuilder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.boot.autoconfigure.http.HttpMessageConvertersAutoConfiguration;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.*;

/**
 * 配置fastjson作为数据返回转换
 * 如果没有配置spring.http.converters.preferred-json-mapper参数则使用该配置进行转换数据返回
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-14 17:00
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Configuration
@ConditionalOnClass({FastJsonHttpMessageConverter.class, ConfigurationBuilder.class})
@AutoConfigureBefore(HttpMessageConvertersAutoConfiguration.class)
@ConditionalOnProperty(
        prefix = "spring.http.converters",
        value = {"preferred-json-mapper"},
        havingValue = "fastJson",
        matchIfMissing = true
)
public class HttpMessageConverterAutoConfiguration {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(HttpMessageConverterAutoConfiguration.class);
    /**
     * value filter package
     */
    private static final String[] VALUE_FILTER_PACKAGE = new String[]{"org.minbox.framework.api.boot.plugin.http.converter.filter"};

    /**
     * 注入bean工厂
     */
    @Autowired
    private BeanFactory beanFactory;

    /**
     * http message converter fastjson实现实例
     * 通过fastjson方式进行格式化返回json字符串
     *
     * @return http message converter
     */
    @Bean
    @ConditionalOnMissingBean
    HttpMessageConverters fastJsonHttpMessageConverters() {
        FastJsonHttpMessageConverter fastConverter = new FastJsonHttpMessageConverter();
        //创建fastJson配置实体类
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(
                SerializerFeature.DisableCircularReferenceDetect,
                SerializerFeature.WriteMapNullValue,
                SerializerFeature.WriteNullListAsEmpty,
                SerializerFeature.WriteNullStringAsEmpty,
                SerializerFeature.WriteNullNumberAsZero,
                SerializerFeature.WriteDateUseDateFormat,
                SerializerFeature.WriteNullBooleanAsFalse
        );

        // get customer define value filters
        List<String> packages = AutoConfigurationPackages.get(beanFactory);
        // get plugin define value filters
        packages.addAll(Arrays.asList(VALUE_FILTER_PACKAGE));

        fastJsonConfig.setSerializeFilters(getDefineFilters(packages));

        fastConverter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(fastConverter);
    }

    /**
     * 获取项目中定义的ValueFilter实现类列表
     * 通过BeanFactory读取本项目的Base Package List
     *
     * @return ValueFilter数组
     */
    ValueFilter[] getDefineFilters(List<String> packages) {
        Set<Class> filterClass = new HashSet<>();
        if (ObjectUtils.isEmpty(packages)) {
            return new ValueFilter[]{};
        }
        // 读取所有package下的ValueFilter实现类
        packages.stream().forEach(pack -> filterClass.addAll((Collection<? extends Class>) ClassTools.getSubClassList(pack, ValueFilter.class)));
        List<ValueFilter> filters = new LinkedList<>();
        filterClass.stream().forEach(filter -> {
            try {
                filters.add((ValueFilter) filter.newInstance());
            } catch (Exception e) {
                logger.error("ValueFilter new instance have error.", e);
            }
        });
        logger.info("Loaded ValueFilter : {}", filterClass.toString());
        return filters.toArray(new ValueFilter[]{});
    }
}
