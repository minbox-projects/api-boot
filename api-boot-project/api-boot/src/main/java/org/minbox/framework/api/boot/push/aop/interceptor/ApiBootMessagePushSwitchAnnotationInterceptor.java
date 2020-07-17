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

package org.minbox.framework.api.boot.push.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.minbox.framework.api.boot.push.annotation.MessagePushSwitch;
import org.minbox.framework.api.boot.push.aop.holder.MessagePushContextHolder;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * ApiBoot DataSource Advice
 * use spring aop
 * switch push implementation based on the {@link MessagePushSwitch} configuration on the method
 *
 * @author 恒宇少年
 */
public class ApiBootMessagePushSwitchAnnotationInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        try {
            Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
            Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
            Method declaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);

            MessagePushSwitch messagePushSwitch = declaredMethod.getDeclaredAnnotation(MessagePushSwitch.class);
            // set current thread message push config name
            MessagePushContextHolder.set(messagePushSwitch.value());
            return invocation.proceed();
        } finally {
            // remove current thread use message push config name
            MessagePushContextHolder.remove();
        }

    }
}
