package org.minbox.framework.api.boot.autoconfigure.enhance;

import org.apache.ibatis.session.Configuration;
import org.mybatis.spring.SqlSessionFactoryBean;

/**
 * The {@link SqlSessionFactoryBean} Customizer
 * <p>
 * implement this interface to perform custom operations on {@link SqlSessionFactoryBean},
 * can support multiple implementation classes,
 * and call the implementation class {@link SqlSessionFactoryBeanCustomizer#customize(SqlSessionFactoryBean)}
 * method at the end of {@link SqlSessionFactoryBean} initialization
 *
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
