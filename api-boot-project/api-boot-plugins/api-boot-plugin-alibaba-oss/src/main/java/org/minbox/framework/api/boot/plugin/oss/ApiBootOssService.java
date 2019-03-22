package org.minbox.framework.api.boot.plugin.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.GetObjectRequest;
import lombok.AllArgsConstructor;
import org.minbox.framework.api.boot.plugin.storage.ApiBootObjectStorageService;
import org.minbox.framework.api.boot.plugin.storage.exception.ApiBootObjectStorageException;
import org.minbox.framework.api.boot.plugin.storage.response.ApiBootObjectStorageResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.InputStream;

/**
 * ApiBoot提供的Oss文件操作类
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 14:02
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengyuboy
 */
@AllArgsConstructor
public class ApiBootOssService implements ApiBootObjectStorageService {
    /**
     * 地域性的endpoint
     */
    protected String endpoint;
    /**
     * 存储空间名称
     */
    protected String bucketName;
    /**
     * 阿里云账号授权id
     */
    protected String accessKeyId;
    /**
     * 阿里云账号授权secret
     */
    protected String accessKeySecret;
    /**
     * 自定义域名
     */
    protected String domain;

    @Override
    public ApiBootObjectStorageResponse upload(String objectName, byte[] bytes) throws ApiBootObjectStorageException {
        try {
            OSSClient ossClient = getOssClient();
            // put byte inputStream
            ossClient.putObject(bucketName, objectName, new ByteArrayInputStream(bytes));
            closeOssClient(ossClient);
        } catch (Exception e) {
            throw new ApiBootObjectStorageException(e.getMessage(), e);
        }
        return ApiBootObjectStorageResponse.builder().objectName(objectName).objectUrl(getObjectUrl(objectName)).build();
    }

    @Override
    public ApiBootObjectStorageResponse upload(String objectName, InputStream inputStream) throws ApiBootObjectStorageException {
        try {
            OSSClient ossClient = getOssClient();
            // put byte inputStream
            ossClient.putObject(bucketName, objectName, inputStream);
            closeOssClient(ossClient);
        } catch (Exception e) {
            throw new ApiBootObjectStorageException(e.getMessage(), e);
        }
        return ApiBootObjectStorageResponse.builder().objectName(objectName).objectUrl(getObjectUrl(objectName)).build();
    }

    @Override
    public ApiBootObjectStorageResponse upload(String objectName, String localFile) throws ApiBootObjectStorageException {
        try {
            OSSClient ossClient = getOssClient();
            // put byte inputStream
            ossClient.putObject(bucketName, objectName, new File(localFile));
            closeOssClient(ossClient);
        } catch (Exception e) {
            throw new ApiBootObjectStorageException(e.getMessage(), e);
        }
        return ApiBootObjectStorageResponse.builder().objectName(objectName).objectUrl(getObjectUrl(objectName)).build();
    }

    @Override
    public void download(String objectName, String localFile) throws ApiBootObjectStorageException {
        try {
            OSSClient ossClient = getOssClient();
            ossClient.getObject(new GetObjectRequest(bucketName, objectName), new File(localFile));
            closeOssClient(ossClient);
        } catch (Exception e) {
            throw new ApiBootObjectStorageException(e.getMessage(), e);
        }
    }

    @Override
    public void delete(String objectName) throws ApiBootObjectStorageException {
        try {
            OSSClient ossClient = getOssClient();
            ossClient.deleteObject(bucketName, objectName);
            closeOssClient(ossClient);
        } catch (Exception e) {
            throw new ApiBootObjectStorageException(e.getMessage(), e);
        }
    }

    /**
     * 获取OssClient对象
     *
     * @return OssClient
     * @throws ApiBootObjectStorageException 对象存储异常对象
     */
    protected OSSClient getOssClient() throws ApiBootObjectStorageException {
        try {
            OSSClient ossClient = new OSSClient(endpoint, accessKeyId, accessKeySecret);
            return ossClient;
        } catch (Exception e) {
            throw new ApiBootObjectStorageException("获取OssClient对象异常.", e);
        }
    }

    /**
     * 关闭OssClient对象
     *
     * @param ossClient OssClient
     * @throws ApiBootObjectStorageException 对象存储异常对象
     */
    protected void closeOssClient(OSSClient ossClient) throws ApiBootObjectStorageException {
        ossClient.shutdown();
    }

    /**
     * 获取默认的文件地址
     * 使用endpoint外网地址进行组合
     *
     * @param objectName 对象文件名称
     * @return 文件访问地址
     */
    protected String getDefaultObjectUrl(String objectName) {
        return String.format("https://%s.%s/%s", bucketName, endpoint.replace("http://", ""), objectName);
    }

    /**
     * 获取文件地址
     *
     * @param objectName 文件对象名称
     * @return 文件访问地址
     */
    protected String getObjectUrl(String objectName) {
        if (domain != null && domain.length() > 0) {
            return String.format(domain + "/%s", objectName);
        }
        return getDefaultObjectUrl(objectName);
    }
}
