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

import org.minbox.framework.api.boot.plugin.logging.ApiBootLog;
import org.minbox.framework.api.boot.plugin.logging.ApiBootLogClientNotice;
import org.minbox.framework.api.boot.plugin.logging.admin.endpoint.LoggingEndpoint;
import org.minbox.framework.api.boot.plugin.logging.admin.event.ReportLogEvent;
import org.minbox.framework.api.boot.plugin.logging.admin.storage.LoggingStorage;
import org.minbox.framework.api.boot.plugin.tools.JsonTools;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.event.SmartApplicationListener;
import org.springframework.util.Assert;
import org.springframework.util.ObjectUtils;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Storage Report Logs Listener
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-22 16:17
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ReportLogStorageListener implements SmartApplicationListener {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ReportLogStorageListener.class);
    /**
     * ServiceDetails IDS
     */
    ConcurrentMap<String, String> SERVICE_DETAIL_IDS = new ConcurrentHashMap();
    /**
     * ApiBoot Logging Storage
     */
    private LoggingStorage loggingStorage;

    public ReportLogStorageListener(LoggingStorage loggingStorage) {
        this.loggingStorage = loggingStorage;
    }

    /**
     * Storage Log
     *
     * @param event ReportLogEvent
     */
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        try {
            logger.debug("Starting Storage Report Request Logs.");
            ReportLogEvent reportLogEvent = (ReportLogEvent) event;
            ApiBootLogClientNotice notice = reportLogEvent.getLogClientNotice();
            String serviceDetail = formatServiceDetailID(notice.getClientServiceId(), notice.getClientServiceIp(), notice.getClientServicePort());
            String serviceDetailId = SERVICE_DETAIL_IDS.get(serviceDetail);

            // new service
            if (ObjectUtils.isEmpty(serviceDetailId)) {
                // select service detail id from database
                serviceDetailId = loggingStorage.selectServiceDetailId(notice.getClientServiceId(), notice.getClientServiceIp(), notice.getClientServicePort());
                // if don't have in database
                // create new service detail
                if (ObjectUtils.isEmpty(serviceDetailId)) {
                    serviceDetailId = loggingStorage.insertServiceDetail(notice.getClientServiceId(), notice.getClientServiceIp(), notice.getClientServicePort());
                }
                if (!ObjectUtils.isEmpty(serviceDetailId)) {
                    SERVICE_DETAIL_IDS.put(serviceDetail, serviceDetailId);
                }
            }

            // save logs
            if (!ObjectUtils.isEmpty(notice.getLoggers())) {
                for (ApiBootLog log : notice.getLoggers()) {
                    loggingStorage.insertLog(serviceDetailId, log);
                }
            }

            // update last report time
            loggingStorage.updateLastReportTime(serviceDetailId);

        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        } finally {
            logger.debug("Storage Report Request Logs Complete.");
        }
    }

    /**
     * format serviceDetail ID
     *
     * @param serviceId   service id
     * @param serviceIp   service ip address
     * @param servicePort service port
     * @return
     */
    String formatServiceDetailID(String serviceId, String serviceIp, Integer servicePort) {
        Assert.notNull(serviceId, "Service Id Is Required.");
        Assert.notNull(serviceIp, "Service Ip Is Required.");
        Assert.notNull(servicePort, "Service Port Is Required.");
        return String.format("%s-%s:%d", serviceId, serviceIp, servicePort);
    }

    @Override
    public boolean supportsEventType(Class<? extends ApplicationEvent> eventType) {
        return eventType == ReportLogEvent.class;
    }

    @Override
    public boolean supportsSourceType(Class<?> sourceType) {
        return sourceType == LoggingEndpoint.class;
    }

    /**
     * second execute
     *
     * @return execute order
     */
    @Override
    public int getOrder() {
        return 1;
    }
}
