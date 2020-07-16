package org.minbox.framework.api.boot.storage;


import org.minbox.framework.api.boot.storage.exception.ApiBootObjectStorageException;
import org.minbox.framework.api.boot.storage.response.ApiBootObjectStorageResponse;

import java.io.InputStream;

/**
 * ApiBoot Object Storage Interface Definition
 *
 * @author 恒宇少年
 */
public interface ApiBootObjectStorageService {
    /**
     * byte array upload file
     *
     * @param objectName file name
     * @param bytes      file byte array
     * @return {@link ApiBootObjectStorageResponse}
     * @throws ApiBootObjectStorageException object storage exception
     */
    ApiBootObjectStorageResponse upload(String objectName, byte[] bytes) throws ApiBootObjectStorageException;

    /**
     * input stream upload file
     *
     * @param objectName  file name
     * @param inputStream file input stream
     * @return {@link ApiBootObjectStorageResponse}
     * @throws ApiBootObjectStorageException object storage exception
     */
    ApiBootObjectStorageResponse upload(String objectName, InputStream inputStream) throws ApiBootObjectStorageException;

    /**
     * local path upload file
     *
     * @param objectName file name
     * @param localFile  file local path
     * @return {@link ApiBootObjectStorageResponse}
     * @throws ApiBootObjectStorageException object storage exception
     */
    ApiBootObjectStorageResponse upload(String objectName, String localFile) throws ApiBootObjectStorageException;

    /**
     * download file
     *
     * @param objectName file name in the object store
     * @param localFile  file local path
     * @throws ApiBootObjectStorageException object storage exception
     */
    void download(String objectName, String localFile) throws ApiBootObjectStorageException;

    /**
     * delete file
     *
     * @param objectName file name in the object store
     * @throws ApiBootObjectStorageException object storage exception
     */
    void delete(String objectName) throws ApiBootObjectStorageException;
}
