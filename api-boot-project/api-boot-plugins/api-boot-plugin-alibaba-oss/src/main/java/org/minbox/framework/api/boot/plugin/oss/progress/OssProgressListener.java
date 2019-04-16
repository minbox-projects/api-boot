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

package org.minbox.framework.api.boot.plugin.oss.progress;

import com.aliyun.oss.event.ProgressEvent;
import com.aliyun.oss.event.ProgressEventType;
import com.aliyun.oss.event.ProgressListener;

import java.math.BigDecimal;

/**
 * ApiBoot Oss Progress Listener
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-04-16 16:05
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class OssProgressListener implements ProgressListener {
    /**
     * already write bytes
     */
    private long bytesWritten = 0;
    /**
     * file total bytes
     */
    private long totalBytes = -1;
    /**
     * percent scale
     */
    private int percentScale = 2;
    /**
     * oss object name
     */
    private String objectName;
    /**
     * ApiBoot Progress
     */
    private ApiBootObjectStorageProgress apiBootObjectStorageProgress;

    public OssProgressListener(String objectName, ApiBootObjectStorageProgress apiBootObjectStorageProgress) {
        this.objectName = objectName;
        this.apiBootObjectStorageProgress = apiBootObjectStorageProgress;
    }

    @Override
    public void progressChanged(ProgressEvent progressEvent) {
        if (apiBootObjectStorageProgress != null) {
            // current progress bytes
            long bytes = progressEvent.getBytes();
            // progress event type
            ProgressEventType eventType = progressEvent.getEventType();

            switch (eventType) {
                // sent file total bytes
                case REQUEST_CONTENT_LENGTH_EVENT:
                    this.totalBytes = bytes;
                    break;
                // request byte transfer
                case REQUEST_BYTE_TRANSFER_EVENT:
                    this.bytesWritten += bytes;
                    if (this.totalBytes != -1) {
                        // Calculation percent
                        double percent = (this.bytesWritten * 100.00 / this.totalBytes);
                        BigDecimal decimal = BigDecimal.valueOf(percent).setScale(percentScale, BigDecimal.ROUND_DOWN);
                        apiBootObjectStorageProgress.progress(objectName, decimal.doubleValue(), this.totalBytes, this.bytesWritten);
                    }
                    break;
                // complete
                case TRANSFER_COMPLETED_EVENT:
                    apiBootObjectStorageProgress.success(objectName);
                    break;
                // failed
                case TRANSFER_FAILED_EVENT:
                    apiBootObjectStorageProgress.failed(objectName);
                    break;
                default:
                    break;
            }
        }
    }
}
