package org.minbox.framework.api.boot.autoconfigure.enhance;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;

/**
 * @author 恒宇少年
 */
public interface SqlSessionFactoryBeanCustomizer {
    /**
     * Customize the given a {@link Configuration} object.
     *
     * @param sqlSessionFactoryBean The {@link SqlSessionFactoryBean} object instance
     */
    void customize(SqlSessionFactoryBean sqlSessionFactoryBean);
}
