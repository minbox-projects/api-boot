package org.minbox.framework.api.boot.sample.message.pipe.server;

import com.alibaba.cloud.nacos.NacosConfigProperties;
import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.minbox.framework.message.pipe.spring.annotation.ServerServiceType;
import org.minbox.framework.message.pipe.spring.annotation.server.EnableMessagePipeServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.ObjectUtils;

import java.util.Properties;

/**
 * 消息管道Server相关配置
 *
 * @author 恒宇少年
 */
@Configuration
@EnableMessagePipeServer(serverType = ServerServiceType.NACOS)
public class MessagePipeServerConfiguration {
    private NacosConfigProperties nacosConfigProperties;

    public MessagePipeServerConfiguration(NacosConfigProperties nacosConfigProperties) {
        this.nacosConfigProperties = nacosConfigProperties;
    }

    /**
     * 配置{@link NamingService}服务实例
     *
     * @return The {@link NamingService} instance
     * @throws NacosException
     */
    @Bean
    public NamingService namingService() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.USERNAME, nacosConfigProperties.getUsername());
        properties.put(PropertyKeyConst.PASSWORD, nacosConfigProperties.getPassword());
        properties.put(PropertyKeyConst.SERVER_ADDR, nacosConfigProperties.getServerAddr());
        if (!ObjectUtils.isEmpty(nacosConfigProperties.getNamespace())) {
            properties.put(PropertyKeyConst.NAMESPACE, nacosConfigProperties.getNamespace());
        }
        return NacosFactory.createNamingService(properties);
    }
}
