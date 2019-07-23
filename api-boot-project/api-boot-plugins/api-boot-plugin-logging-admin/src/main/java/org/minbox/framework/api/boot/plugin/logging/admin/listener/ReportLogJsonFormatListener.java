/*
 * Copyright [2019] [恒宇少年 - 于起宇]
 *
 *      Licensed under the Apache License, Version 2.0 (the "License");
 *      you may not use this file except in compliance with the License.
 *      You may obtain a copy of the License at
 *
 *          http://www.apache.org/licenses/LICENSE-2.0
 *
 *      Unless required by applicable law or agreed to in writing, software
 *      distributed under the License is distributed on an "AS IS" BASIS,
 *      WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *      See the License for the specific language governing permissions and
 *      limitations under the License.
 *
 */

package org.minbox.framework.api.boot.plugin.logging.admin.listener;

import com.alibaba.fastjson.JSON;
import org.minbox.framework.api.boot.plugin.logging.ApiBootLogClientNotice;
import org.minbox.framework.api.boot.plugin.logging.admin.endpoint.LoggingEndpoint;
import org.minbox.framework.api.boot.plugin.logging.admin.event.ReportLogEvent;
import org.minbox.framework.api.boot.plugin.tools.JsonTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;

/**
 * Report Log Json Format Listener
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-22 15:24
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 * @see ReportLogEvent
 */
public class ReportLogJsonFormatListener implements SmartApplicationListener {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ReportLogJsonFormatListener.class);
    /**
     * Whether to print the logs reported on the console
     */
    private boolean showConsoleReportLog;
    /**
     * Format console log JSON
     */
    private boolean formatConsoleLogJson;

    public ReportLogJsonFormatListener(boolean showConsoleReportLog, boolean formatConsoleLogJson) {
        this.showConsoleReportLog = showConsoleReportLog;
        this.formatConsoleLogJson = formatConsoleLogJson;
    }

    /**
     * Report logs on console output
     * Format Update Log
     *
     * @param event ReportLogEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try {
            ReportLogEvent reportLogEvent = (ReportLogEvent) event;
            if (showConsoleReportLog) {
                ApiBootLogClientNotice notice = reportLogEvent.getLogClientNotice();
                String serviceInfo = String.format("%s -> %s", notice.getClientServiceId(), notice.getClientServiceIp());
                logger.info("Receiving Service: 【{}】, Request Log Report，Logging Content：{}", serviceInfo, formatConsoleLogJson ? JsonTools.beautifyJson(notice.getLoggers()) : JSON.toJSONString(notice.getLoggers()));
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == LoggingEndpoint.class;
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ReportLogEvent.class;
    }

    /**
     * first execute
     *
     * @return execute order
     */
    @Override
    public int getOrder() {
        return 0;
    }
}
