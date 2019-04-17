package org.minbox.framework.api.boot.plugin.oss;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.*;
import lombok.Setter;
import org.minbox.framework.api.boot.plugin.oss.progress.ApiBootObjectStorageProgress;
import org.minbox.framework.api.boot.plugin.oss.progress.OssProgressListener;
import org.minbox.framework.api.boot.plugin.storage.ApiBootObjectStorageService;
import org.minbox.framework.api.boot.plugin.storage.exception.ApiBootObjectStorageException;
import org.minbox.framework.api.boot.plugin.storage.response.ApiBootObjectStorageResponse;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * ApiBoot提供的Oss文件操作类
 *
 * @author：恒宇少年 - 于起宇
 * <p>
 * DateTime：2019-03-21 14:02
 * Blog：http://blog.yuqiyu.com
 * WebSite：http://www.jianshu.com/u/092df3f77bca
 * Gitee：https://gitee.com/hengboy
 * GitHub：https://github.com/hengboy
 */
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
    /**
     * ApiBoot Oss Progress
     */
    @Setter
    private ApiBootObjectStorageProgress apiBootObjectStorageProgress;

    public ApiBootOssService(String endpoint, String bucketName, String accessKeyId, String accessKeySecret, String domain) {
        this.endpoint = endpoint;
        this.bucketName = bucketName;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.domain = domain;
    }

    @Override
    public ApiBootObjectStorageResponse upload(String objectName, byte[] bytes) throws ApiBootObjectStorageException {
        try {
            OSSClient ossClient = getOssClient();
            // put byte inputStream
            ossClient.putObject(new PutObjectRequest(bucketName, objectName, new ByteArrayInputStream(bytes)).withProgressListener(new OssProgressListener(objectName, apiBootObjectStorageProgress)));
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
            ossClient.putObject(new PutObjectRequest(bucketName, objectName, inputStream).withProgressListener(new OssProgressListener(objectName, apiBootObjectStorageProgress)));
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
            ossClient.putObject(new PutObjectRequest(bucketName, objectName, new File(localFile)).withProgressListener(new OssProgressListener(objectName, apiBootObjectStorageProgress)));
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
            ossClient.getObject(new GetObjectRequest(bucketName, objectName).withProgressListener(new OssProgressListener(objectName, apiBootObjectStorageProgress)), new File(localFile));
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
     * multi part upload file
     * with local file
     *
     * @param objectName object name
     * @param uploadFile upload file
     * @param partSize   every part size
     * @return ApiBootObjectStorageResponse
     * @throws ApiBootObjectStorageException ApiBoot Oss Exception
     */
    public ApiBootObjectStorageResponse multipartUpload(String objectName, File uploadFile, long partSize) throws ApiBootObjectStorageException {
        try {
            OSSClient ossClient = getOssClient();

            // init multi part upload request
            InitiateMultipartUploadRequest request = new InitiateMultipartUploadRequest(bucketName, objectName);

            // get upload id
            InitiateMultipartUploadResult result = ossClient.initiateMultipartUpload(request);
            String uploadId = result.getUploadId();
            List<PartETag> partETags = new ArrayList();
            // local file length
            long fileLength = uploadFile.length();
            // part count
            int partCount = (int) (fileLength / partSize);

            if (fileLength % partSize != 0) {
                partCount++;
            }

            for (int i = 0; i < partCount; i++) {
                // start position
                long startPos = i * partSize;
                // current part size
                long curPartSize = (i + 1 == partCount) ? (fileLength - startPos) : partSize;

                InputStream is = new FileInputStream(uploadFile);
                is.skip(startPos);

                UploadPartRequest uploadPartRequest = new UploadPartRequest();
                uploadPartRequest.setBucketName(bucketName);
                uploadPartRequest.setKey(objectName);
                uploadPartRequest.setUploadId(uploadId);
                uploadPartRequest.setInputStream(is);
                // set part size
                uploadPartRequest.setPartSize(curPartSize);
                // set part number
                uploadPartRequest.setPartNumber(i + 1);

                // execute upload part
                UploadPartResult uploadPartResult = ossClient.uploadPart(uploadPartRequest);
                partETags.add(uploadPartResult.getPartETag());
            }

            // sort by part number
            Collections.sort(partETags, Comparator.comparingInt(PartETag::getPartNumber));

            // merge upload part file
            CompleteMultipartUploadRequest completeMultipartUploadRequest = new CompleteMultipartUploadRequest(bucketName, objectName, uploadId, partETags);
            ossClient.completeMultipartUpload(completeMultipartUploadRequest);
            closeOssClient(ossClient);
        } catch (Exception e) {
            throw new ApiBootObjectStorageException(e.getMessage(), e);
        }
        return ApiBootObjectStorageResponse.builder().objectName(objectName).objectUrl(getObjectUrl(objectName)).build();
    }

    /**
     * multi part upload file
     * with local file string path
     *
     * @param objectName object name
     * @param localFile  local file
     * @param partSize   every part size
     * @return ApiBootObjectStorageResponse
     * @throws ApiBootObjectStorageException ApiBoot Oss Exception
     * @see PartSize
     */
    public ApiBootObjectStorageResponse multipartUpload(String objectName, String localFile, long partSize) throws ApiBootObjectStorageException {
        // load local file
        File uploadFile = new File(localFile);
        // execute multi part upload file
        return multipartUpload(objectName, uploadFile, partSize);
    }

    /**
     * 获取OssClient对象
     *
     * @return OssClient
     * @throws ApiBootObjectStorageException ApiBoot Oss Exception
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
