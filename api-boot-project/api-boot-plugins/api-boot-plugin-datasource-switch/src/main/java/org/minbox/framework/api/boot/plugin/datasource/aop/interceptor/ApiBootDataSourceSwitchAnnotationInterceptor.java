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
 * ApiBoot DataSource Advice
 * use spring aop
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 16:44
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootDataSourceSwitchAnnotationInterceptor implements MethodInterceptor {

    @Override
    public Object invoke(MethodInvocation invocation) throws Throwable {
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
            return invocation.proceed();
        } finally {
            // remove current thread use datasource pool name
            DataSourceContextHolder.remove();
        }

    }
}
