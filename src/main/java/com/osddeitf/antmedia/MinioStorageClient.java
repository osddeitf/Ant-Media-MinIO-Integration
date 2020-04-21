package com.osddeitf.antmedia;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.logging.Logger;

import org.apache.commons.lang3.exception.ExceptionUtils;

import io.antmedia.storage.StorageClient;
import io.minio.ErrorCode;
import io.minio.MinioClient;
import io.minio.PutObjectOptions;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import okhttp3.HttpUrl;

public class MinioStorageClient extends StorageClient {

    private MinioClient minioClient;

	protected static Logger logger = Logger.getLogger(MinioStorageClient.class.getName());

    private MinioClient getMinioClient() throws InvalidEndpointException, InvalidPortException {
        if (minioClient == null) {
            minioClient = new MinioClient(
                HttpUrl.parse(this.getRegion()),
                this.getAccessKey(),
                this.getSecretKey()
            );
        }
        return minioClient;
    }

    @Override
    public boolean fileExist(String filename, FileType type) {
        try {
            getMinioClient().statObject(getStorageName(), filename);
            return true;
        }
        catch (ErrorResponseException e) {
            if (e.errorResponse().errorCode() == ErrorCode.NO_SUCH_OBJECT)
                return false;
            logger.log(java.util.logging.Level.SEVERE, e.toString());
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, ExceptionUtils.getStackTrace(e));
        }
        return false;
    }

    @Override
    public void delete(String filename, FileType type) {
        try {
            getMinioClient().removeObject(getStorageName(), filename);
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, ExceptionUtils.getStackTrace(e));
        }
    }

    @Override
    public void save(File file, FileType type) {
        try {
            logger.info("Mp4 " + file.getName() + " upload has started");

            InputStream inputStream = new FileInputStream(file);
            getMinioClient().putObject(
                getStorageName(),
                file.getName(),
                new FileInputStream(file),
                new PutObjectOptions(inputStream.available(), 10*1024*1024)
            );

            logger.info("File " + file.getName() + " uploaded to MinIO");
        }
        catch (Exception e) {
            logger.log(java.util.logging.Level.SEVERE, ExceptionUtils.getStackTrace(e));
        }
    }
}
