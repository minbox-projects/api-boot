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

import org.minbox.framework.api.boot.plugin.storage.exception.ApiBootObjectStorageException;

/**
 * ApiBoot Oss Upload and Download Progress
 *
 * @author：恒宇少年 - 于起宇
 *
 * DateTime：2019-04-16 17:05
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface ApiBootObjectStorageProgress {
    /**
     * progress
     *
     * @param objectName          object name
     * @param percent             upload or download progress percent
     * @param totalBytes          total bytes
     * @param currentWrittenBytes already written bytes
     * @throws ApiBootObjectStorageException ApiBoot Oss Exception
     */
    void progress(String objectName, double percent, long totalBytes, long currentWrittenBytes) throws ApiBootObjectStorageException;

    /**
     * upload or download success
     *
     * @param objectName object name
     * @throws ApiBootObjectStorageException ApiBoot Oss Exception
     */
    void success(String objectName) throws ApiBootObjectStorageException;

    /**
     * upload or download failed
     *
     * @param objectName object name
     * @throws ApiBootObjectStorageException ApiBoot Oss Exception
     */
    void failed(String objectName) throws ApiBootObjectStorageException;
}
