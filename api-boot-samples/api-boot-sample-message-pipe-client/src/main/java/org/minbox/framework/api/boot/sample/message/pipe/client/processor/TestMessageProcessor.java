package org.minbox.framework.api.boot.sample.message.pipe.client.processor;

import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.message.pipe.client.process.MessageProcessor;
import org.springframework.stereotype.Service;

/**
 * 示例 {@link MessageProcessor}
 *
 * @author 恒宇少年
 */
@Slf4j
@Service
public class TestMessageProcessor implements MessageProcessor {
    private static final String TEST_PIPE_NAME = "test";

    @Override
    public String bindingPipeName() {
        return TEST_PIPE_NAME;
    }

    @Override
    public boolean processing(String requestId, byte[] messageBody) {
        log.info("消费消息：{}，内容：{}", requestId, new String(messageBody));
        return true;
    }
}
