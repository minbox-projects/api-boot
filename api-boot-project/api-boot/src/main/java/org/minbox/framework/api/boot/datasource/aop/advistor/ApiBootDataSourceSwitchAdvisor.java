package org.minbox.framework.api.boot.datasource.aop.advistor;

import org.aopalliance.aop.Advice;
import org.minbox.framework.api.boot.datasource.annotation.DataSourceSwitch;
import org.minbox.framework.api.boot.datasource.aop.interceptor.ApiBootDataSourceSwitchAnnotationInterceptor;
import org.springframework.aop.Pointcut;
import org.springframework.aop.support.AbstractPointcutAdvisor;
import org.springframework.aop.support.ComposablePointcut;
import org.springframework.aop.support.annotation.AnnotationMatchingPointcut;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.util.Assert;

/**
 * ApiBoot DataSource Switch {@link org.springframework.aop.PointcutAdvisor}
 *
 * @author 恒宇少年
 */
public class ApiBootDataSourceSwitchAdvisor extends AbstractPointcutAdvisor implements BeanFactoryAware {
    /**
     * Method Aspect Implementation
     *
     * @see ApiBootDataSourceSwitchAnnotationInterceptor
     */
    private Advice advice;
    /**
     * use {@link DataSourceSwitch} annotations as entry points
     */
    private Pointcut pointcut;
    /**
     * the spring {@link BeanFactory}
     */
    private BeanFactory beanFactory;

    /**
     * Initialize global variables using constructor
     *
     * @param apiBootDataSourceSwitchAnnotationInterceptor {@link ApiBootDataSourceSwitchAnnotationInterceptor}
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
     * use {@link DataSourceSwitch} as a {@link Pointcut}
     * scope：
     * 1. {@link DataSourceSwitch} on the class
     * 2. {@link DataSourceSwitch} on the method
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

    /**
     * Highest priority
     *
     * @return {@link org.springframework.aop.PointcutAdvisor} Priority value
     */
    @Override
    public int getOrder() {
        return HIGHEST_PRECEDENCE;
    }
}
