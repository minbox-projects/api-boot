package org.minbox.framework.api.boot.sample.message.pipe.server;

import org.minbox.framework.message.pipe.core.Message;
import org.minbox.framework.message.pipe.server.MessagePipe;
import org.minbox.framework.message.pipe.server.manager.MessagePipeManager;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;

import java.util.UUID;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

/**
 * 将消息{@link Message}定时写入消息管道{@link MessagePipe}测试类
 *
 * @author 恒宇少年
 */
@Configuration
public class PutMessage implements InitializingBean {
    @Autowired
    private MessagePipeManager messagePipeManager;
    private ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(1);

    @Override
    public void afterPropertiesSet() throws Exception {
        scheduledExecutorService.scheduleWithFixedDelay(() -> {
            MessagePipe messagePipe = messagePipeManager.getMessagePipe("test");
            // 随机uuid作为内容
            String content = UUID.randomUUID().toString();
            Message message = new Message(content.getBytes());
            // 设置元数据
            message.getMetadata().put("traceId", System.currentTimeMillis());
            messagePipe.putLast(message);
        }, 1000, 1000, TimeUnit.MILLISECONDS);
    }
}
