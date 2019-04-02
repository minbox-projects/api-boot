package org.minbox.framework.api.boot.plugin.datasource.aop.advistor;

import org.aopalliance.aop.Advice;
import org.minbox.framework.api.boot.plugin.datasource.annotation.DataSourceSwitch;
import org.minbox.framework.api.boot.plugin.datasource.aop.interceptor.ApiBootDataSourceSwitchAnnotationInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.Assert;

/**
 * ApiBoot DataSource Switch
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-01 16:29
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootDataSourceSwitchAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {

    private Advice advice;
    private Pointcut pointcut;
    private BeanFactory beanFactory;

    /**
     * init config
     */
    public ApiBootDataSourceSwitchAdvisor(ApiBootDataSourceSwitchAnnotationInterceptor apiBootDataSourceSwitchAnnotationInterceptor) {
        // build pointcut instance
        this.pointcut = buildPointcut();
        // build advice instance
        this.advice = apiBootDataSourceSwitchAnnotationInterceptor;
        if (this.advice instanceof BeanFactoryAware) {
            ((BeanFactoryAware) this.advice).setBeanFactory(beanFactory);
        }
    }

    @Override
    public Pointcut getPointcut() {
        Assert.notNull(this.pointcut, "pointcut is required.");
        return this.pointcut;
    }

    @Override
    public Advice getAdvice() {
        Assert.notNull(this.advice, "advice is required.");
        return this.advice;
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = beanFactory;
    }

    /**
     * build pointcut instance
     */
    private Pointcut buildPointcut() {
        // class
        Pointcut cpc = new AnnotationMatchingPointcut(DataSourceSwitch.class, true);
        // method
        Pointcut mpc = AnnotationMatchingPointcut.forMethodAnnotation(DataSourceSwitch.class);

        //union
        ComposablePointcut pointcut = new ComposablePointcut(cpc);
        return pointcut.union(mpc);
    }

}
