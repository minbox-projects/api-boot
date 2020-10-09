package org.minbox.framework.api.boot.sample.message.pipe.server;

import com.alibaba.nacos.api.NacosFactory;
import com.alibaba.nacos.api.PropertyKeyConst;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import org.minbox.framework.message.pipe.server.processing.push.PushMessageEvent;
import org.minbox.framework.message.pipe.spring.annotation.ServerServiceType;
import org.minbox.framework.message.pipe.spring.annotation.server.EnableMessagePipeServer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;

import java.util.Properties;

/**
 * 消息管道Server相关配置
 *
 * @author 恒宇少年
 */
@Configuration
@EnableMessagePipeServer(serverType = ServerServiceType.NACOS)
public class MessagePipeServerConfiguration {
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
