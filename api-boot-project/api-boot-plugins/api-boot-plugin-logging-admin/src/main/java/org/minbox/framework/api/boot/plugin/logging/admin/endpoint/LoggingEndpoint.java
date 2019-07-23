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

package org.minbox.framework.api.boot.plugin.logging.admin.endpoint;

import org.minbox.framework.api.boot.plugin.logging.ApiBootLogClientNotice;
import org.minbox.framework.api.boot.plugin.logging.ReportResponse;
import org.minbox.framework.api.boot.plugin.logging.admin.event.ReportLogEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * ApiBoot Logging Endpoint Controller
 * Receive Log report
 * Provide log analysis
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-07-19 09:50
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@Endpoint
public class LoggingEndpoint {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(LoggingEndpoint.class);

    /**
     * Application Context
     */
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * Receive ApiBoot Logging Client Report Request Log
     *
     * @param notice ApiBoot Logging Client Notice Log Instance
     * @return report status
     */
    @PostMapping(value = "/logging/report", consumes = MediaType.APPLICATION_PROBLEM_JSON_UTF8_VALUE)
    @ResponseBody
    public ResponseEntity<ReportResponse> report(@RequestBody ApiBootLogClientNotice notice) {
        // is report success
        boolean reportSuccess = true;
        try {
            // publish ReportLogEvent
            // Persistence logs are handed over to listeners
            applicationContext.publishEvent(new ReportLogEvent(this, notice));
        } catch (Exception e) {
            reportSuccess = false;
            logger.error(e.getMessage(), e);
        }
        ReportResponse response = new ReportResponse();
        response.setStatus(reportSuccess ? ReportResponse.SUCCESS : ReportResponse.ERROR);
        return new ResponseEntity(response, HttpStatus.OK);
    }
}
