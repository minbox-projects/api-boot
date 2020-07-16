package org.minbox.framework.api.boot.sample;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.minbox.framework.api.boot.oss.ApiBootOssService;
import org.minbox.framework.api.boot.storage.response.ApiBootObjectStorageResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.File;
import java.io.FileInputStream;

/**
 * ApiBoot Alibaba Oss 单元测试
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 15:41
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = ApiBootOssSampleApplication.class)
public class ApiBootOssTest {

    /**
     * logger instance
     */
    static Logger logger = LoggerFactory.getLogger(ApiBootOssTest.class);
    /**
     * 注入ApiBoot内置Oss Service
     */
    @Autowired
    private ApiBootOssService apiBootOssService;
    /**
     * 注入自定义Oss Service
     */
    @Autowired
    private ApiBootOssOverrideService apiBootOssOverrideService;

    /**
     * 流方式上传
     */
    @Test
    public void uploadBytes() {
        ApiBootObjectStorageResponse response = apiBootOssService.upload("admin.txt", "admin".getBytes());
        logger.info("文件名称：{}", response.getObjectName());
        logger.info("文件访问路径：{}", response.getObjectUrl());
    }

    /**
     * 本地文件上传
     */
    @Test
    public void uploadFile() {
        ApiBootObjectStorageResponse response = apiBootOssService.upload("ApiBoot Security Oauth内存方式集成.mp4", "/Users/yuqiyu/Movies/ApiBoot/ApiBoot Security Oauth内存方式集成.mp4");
        logger.info("文件名称：{}", response.getObjectName());
        logger.info("文件访问路径：{}", response.getObjectUrl());
    }

    /**
     * 文件流方式上传
     *
     * @throws Exception
     */
    @Test
    public void uploadInputStream() throws Exception {
        FileInputStream inputStream = new FileInputStream(new File("/Users/yuqiyu/Downloads/update-release-v10102.apk"));
        ApiBootObjectStorageResponse response = apiBootOssService.upload("update-release-v10102.apk", inputStream);
        logger.info("文件名称：{}", response.getObjectName());
        logger.info("文件访问路径：{}", response.getObjectUrl());
    }

    /**
     * 删除文件示例
     */
    @Test
    public void delete() {
        apiBootOssOverrideService.delete("logo.png");
    }

    /**
     * 下载文件
     */
    @Test
    public void download() {
        apiBootOssOverrideService.download("测试.png", "/Users/yuqiyu/Downloads/测试.png");
    }

    @Test
    public void createBucket() {
        apiBootOssOverrideService.createBucket("micro-job");
    }

}
