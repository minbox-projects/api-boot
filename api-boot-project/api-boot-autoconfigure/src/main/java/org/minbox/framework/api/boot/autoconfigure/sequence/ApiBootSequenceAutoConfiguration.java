package org.minbox.framework.api.boot.autoconfigure.sequence;

import org.minbox.framework.sequence.Sequence;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 分布式高效ID "Sequence" 自动化配置类
 *
 * @author 恒宇少年
 */
@Configuration
@ConditionalOnClass(Sequence.class)
@EnableConfigurationProperties(ApiBootSequenceProperties.class)
public class ApiBootSequenceAutoConfiguration {
    /**
     * 注入 "Sequence" 所需要的属性配置类
     */
    private final ApiBootSequenceProperties apiBootSequenceProperties;

    public ApiBootSequenceAutoConfiguration(ApiBootSequenceProperties apiBootSequenceProperties) {
        this.apiBootSequenceProperties = apiBootSequenceProperties;
    }

    /**
     * 实例化 {@link ApiBootSequenceContext}
     * <p>
     * 在{@link ApiBootSequenceContext}内进行实例化{@link Sequence}，
     * 部分配置的默认值请参考{@link ApiBootSequenceProperties}
     *
     * @return Sequence instance object
     */
    @Bean
    @ConditionalOnMissingBean
    public ApiBootSequenceContext apiBootSequenceContext() {
        return new ApiBootSequenceContext(apiBootSequenceProperties);
    }
}
