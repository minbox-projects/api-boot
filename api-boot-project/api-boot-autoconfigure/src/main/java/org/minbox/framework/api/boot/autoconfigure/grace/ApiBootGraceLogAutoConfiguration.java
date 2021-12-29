package org.minbox.framework.api.boot.autoconfigure.grace;

import org.minbox.framework.grace.core.GraceRecorderAopBeanDefinitionRegistrar;
import org.minbox.framework.grace.core.operator.GraceLoadOperatorService;
import org.minbox.framework.grace.expression.ExpressionFunctionFactory;
import org.minbox.framework.grace.expression.annotation.GraceFunction;
import org.minbox.framework.grace.expression.annotation.GraceFunctionDefiner;
import org.minbox.framework.grace.processor.GraceLogStorageProcessor;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigurationPackages;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Primary;
import org.springframework.util.ObjectUtils;

import java.util.List;

/**
 * Grace操作日志框架自动化配置类
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass({ExpressionFunctionFactory.class, GraceLogStorageProcessor.class, GraceLoadOperatorService.class})
@Import(GraceRecorderAopBeanDefinitionRegistrar.class)
@EnableConfigurationProperties(ApiBootGraceLogProperties.class)
public class ApiBootGraceLogAutoConfiguration {
    private BeanFactory beanFactory;
    private ApiBootGraceLogProperties graceLogProperties;

    public ApiBootGraceLogAutoConfiguration(BeanFactory beanFactory, ApiBootGraceLogProperties graceLogProperties) {
        this.beanFactory = beanFactory;
        this.graceLogProperties = graceLogProperties;
    }

    /**
     * 创建表达式函数工厂类实例
     * 该类实例化后会自动加载指定"Package"内配置{@link GraceFunctionDefiner}的类
     * 并获取类内配置{@link GraceFunction}的函数列表
     *
     * @return {@link ExpressionFunctionFactory}
     */
    @Bean
    @Primary
    public ExpressionFunctionFactory expressionFunctionFactory() {
        List<String> basePackageList = this.graceLogProperties.getFunctionScanBasePackages();
        if (ObjectUtils.isEmpty(basePackageList)) {
            basePackageList = AutoConfigurationPackages.get(this.beanFactory);
        }
        return new ExpressionFunctionFactory(basePackageList);
    }
}
