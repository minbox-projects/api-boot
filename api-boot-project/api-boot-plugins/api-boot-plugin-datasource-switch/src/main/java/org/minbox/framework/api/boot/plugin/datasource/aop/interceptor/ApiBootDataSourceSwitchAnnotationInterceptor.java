package org.minbox.framework.api.boot.plugin.datasource.aop.interceptor;

import org.aopalliance.intercept.MethodInterceptor;
import org.aopalliance.intercept.MethodInvocation;
import org.minbox.framework.api.boot.plugin.datasource.annotation.DataSourceSwitch;
import org.minbox.framework.api.boot.plugin.datasource.routing.DataSourceContextHolder;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.BridgeMethodResolver;
import org.springframework.util.ClassUtils;

import java.lang.reflect.Method;

/**
 * Aspects of the method provided by the ApiBoot DataSource Switch
 * First get the {@link DataSourceSwitch} annotation instance from the class. If it is null,
 * use the annotation configuration on the method.
 *
 * @author 恒宇少年
 */
public class ApiBootDataSourceSwitchAnnotationInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
        Object methodResult;
        try {
            Class<?> targetClass = (invocation.getThis() != null ? AopUtils.getTargetClass(invocation.getThis()) : null);
            Method specificMethod = ClassUtils.getMostSpecificMethod(invocation.getMethod(), targetClass);
            Method userDeclaredMethod = BridgeMethodResolver.findBridgedMethod(specificMethod);
            // get class declared DataSourceSwitch annotation
            DataSourceSwitch dataSourceSwitch = targetClass.getDeclaredAnnotation(DataSourceSwitch.class);
            if (dataSourceSwitch == null) {
                // get declared DataSourceSwitch annotation
                dataSourceSwitch = userDeclaredMethod.getDeclaredAnnotation(DataSourceSwitch.class);
            }
            if (dataSourceSwitch != null) {
                // setting current thread use data source pool name
                DataSourceContextHolder.set(dataSourceSwitch.value());
            }
            methodResult = invocation.proceed();
        } finally {
            // remove current thread use datasource pool name
            DataSourceContextHolder.remove();
        }
        return methodResult;

    }
}
