package org.minbox.framework.api.boot.sample;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.Bucket;
import org.minbox.framework.oss.support.aliyun.AliyunObjectStorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 自定义扩展Oss Service
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-22 10:00
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
public class ApiBootOssOverrideService extends AliyunObjectStorageService {
    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootOssOverrideService.class);

    /**
     * 实现父类构造函数
     *
     * @param endpoint        外网节点
     * @param bucketName      存储空间名称
     * @param accessKeyId     阿里云账号授权Id
     * @param accessKeySecret 阿里云账号授权Secret
     * @param domain          自定义域名
     */
    public ApiBootOssOverrideService(String endpoint, String bucketName, String accessKeyId, String accessKeySecret, String domain) {
        super(endpoint, bucketName, accessKeyId, accessKeySecret, domain);
    }

    /**
     * 创建bucket存储
     *
     * @param bucketName 存储名称
     */
    public void createBucket(String bucketName) {
        OSSClient ossClient = getOssClient();
        Bucket bucket = ossClient.createBucket(bucketName);
        logger.info("新创建存储空间名称：{}", bucket.getName());
        logger.info("新创建存储空间所属人：{}", bucket.getOwner().getDisplayName());
        closeOssClient(ossClient);
    }
}
