package org.minbox.framework.api.boot.sample.message.pipe.client;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.minbox.framework.message.pipe.spring.annotation.ServerServiceType;
import org.minbox.framework.message.pipe.spring.annotation.client.EnableMessagePipeClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Properties;

/**
 * 消息管道客户端配置示例
 * <p>
 * 使用Nacos方式进行注册服务
 *
 * @author 恒宇少年
 */
@Configuration
@EnableMessagePipeClient(serverType = ServerServiceType.NACOS)
public class MessagePipeClientConfiguration {
    /**
     * 配置Nacos {@link NamingService}实例
     *
     * @return
     * @throws NacosException
     */
    @Bean
    public NamingService namingService() throws NacosException {
        Properties properties = new Properties();
        properties.put(PropertyKeyConst.USERNAME, "nacos");
        properties.put(PropertyKeyConst.PASSWORD, "nacos");
        properties.put(PropertyKeyConst.SERVER_ADDR, "open.nacos.yuqiyu.com:80");
        return NacosFactory.createNamingService(properties);
    }
}
