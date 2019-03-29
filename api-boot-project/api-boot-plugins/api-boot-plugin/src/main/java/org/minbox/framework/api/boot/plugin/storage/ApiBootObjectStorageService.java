package org.minbox.framework.api.boot.plugin.storage;

import org.minbox.framework.api.boot.plugin.storage.exception.ApiBootObjectStorageException;
import org.minbox.framework.api.boot.plugin.storage.response.ApiBootObjectStorageResponse;

import java.io.InputStream;

/**
 * ApiBoot 对象存储接口定义
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 14:05
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public interface ApiBootObjectStorageService {
    /**
     * 流式上传
     *
     * @param objectName 文件对象名称
     * @param bytes      流数组
     * @return 响应实体
     * @throws ApiBootObjectStorageException 对象存储异常对象
     */
    ApiBootObjectStorageResponse upload(String objectName, byte[] bytes) throws ApiBootObjectStorageException;

    /**
     * 文件流方式上传
     *
     * @param objectName  文件对象名称
     * @param inputStream 文件输入流
     * @return 响应实体
     * @throws ApiBootObjectStorageException 对象存储异常对象
     */
    ApiBootObjectStorageResponse upload(String objectName, InputStream inputStream) throws ApiBootObjectStorageException;

    /**
     * 本地文件路径方式上传
     *
     * @param objectName 文件对象名称
     * @param localFile  文件路径
     * @return 响应实体
     * @throws ApiBootObjectStorageException 对象存储异常对象
     */
    ApiBootObjectStorageResponse upload(String objectName, String localFile) throws ApiBootObjectStorageException;

    /**
     * 下载文件写入本地文件
     *
     * @param objectName 存储对象名称
     * @param localFile  本地文件路径
     * @throws ApiBootObjectStorageException 对象存储异常对象
     */
    void download(String objectName, String localFile) throws ApiBootObjectStorageException;

    /**
     * 删除文件
     *
     * @param objectName 文件对象名称
     * @throws ApiBootObjectStorageException 对象存储异常对象
     */
    void delete(String objectName) throws ApiBootObjectStorageException;
}
