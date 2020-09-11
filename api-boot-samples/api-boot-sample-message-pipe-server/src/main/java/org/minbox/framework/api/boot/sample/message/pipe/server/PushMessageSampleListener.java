package org.minbox.framework.api.boot.sample.message.pipe.server;

import lombok.extern.slf4j.Slf4j;
import org.minbox.framework.message.pipe.server.processing.push.PushMessageEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

/**
 * The {@link PushMessageEvent} sample
 *
 * @author 恒宇少年
 */
@Component
@Slf4j
public class PushMessageSampleListener implements ApplicationListener<PushMessageEvent> {
    @Override
    public void onApplicationEvent(PushMessageEvent event) {
        log.info("消息管道：{}，写入新消息.", event.getPipeName());
    }
}
