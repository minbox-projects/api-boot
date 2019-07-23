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

package org.minbox.framework.api.boot.plugin.logging.admin.endpoint;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 * ApiBoot Logging RequestMapping Handler
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 09:53
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class LoggingRequestMappingHandlerMapping extends RequestMappingHandlerMapping {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingRequestMappingHandlerMapping.class);
    /**
     * logging handler method map
     */
    private final Map<String, HandlerMethod> loggingHandlerMethods = new LinkedHashMap<>();
    /**
     * Endpoint Object
     */
    private final Object handler;

    public LoggingRequestMappingHandlerMapping(Object handler) {
        this.handler = handler;
    }

    /**
     * Only Endpoint Class with @Endpoint annotation is processed
     *
     * @param beanType endpoint bean type class
     * @return is handler
     */
    @Override
    protected boolean isHandler(Class<?> beanType) {
        return AnnotationUtils.findAnnotation(beanType, Endpoint.class) != null;
    }

    /**
     * Init ApiBoot Logging Endpoint Object Methods
     */
    @Override
    protected void initHandlerMethods() {
        logger.debug("ApiBoot Logging Endpoint Load Init.");
        Class<?> clazz = handler.getClass();
        setOrder(Ordered.HIGHEST_PRECEDENCE + 1000);
        if (isHandler(clazz)) {
            Method[] methods = clazz.getMethods();
            Arrays.stream(methods).forEach(method -> {
                if (AnnotationUtils.findAnnotation(method, RequestMapping.class) != null) {
                    RequestMappingInfo mapping = getMappingForMethod(method, clazz);
                    HandlerMethod handlerMethod = createHandlerMethod(handler, method);
                    mapping.getPatternsCondition().getPatterns().stream().forEach(path -> {
                        logger.debug("Mapped ApiBoot Logging Endpoint URL [{}]", path);
                        loggingHandlerMethods.put(path, handlerMethod);
                    });
                }
            });
        }
        logger.debug("ApiBoot Logging Endpoint Load Successfully.");
    }

    /**
     * The lookup handler method, maps the SEOMapper method to the request URL.
     * <p>If no mapping is found, or if the URL is disabled, it will simply drop through
     * to the standard 404 handling.</p>
     *
     * @param lookupPath lookup path
     * @param request    http servlet request
     * @return The HandlerMethod if one was found.
     * @throws Exception exception
     */
    @Override
    protected HandlerMethod lookupHandlerMethod(String lookupPath, HttpServletRequest request) throws Exception {
        logger.debug("looking up handler for path: " + lookupPath);
        HandlerMethod handlerMethod = loggingHandlerMethods.get(lookupPath);

        if (RequestMethod.POST.toString().equals(request.getMethod()) && handlerMethod != null) {
            return handlerMethod;
        }
        return null;
    }

}
