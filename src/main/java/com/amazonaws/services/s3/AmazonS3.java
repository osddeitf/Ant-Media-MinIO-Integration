package com.amazonaws.services.s3;

import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import io.minio.ErrorCode;
import io.minio.MinioClient;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;
import okhttp3.HttpUrl;

public class AmazonS3 extends MinioClient {

    public AmazonS3(String endpoint, String accessKey, String secretKey, boolean secure)
            throws InvalidEndpointException, InvalidPortException {
        super(HttpUrl.parse(endpoint), accessKey, secretKey);
    }

    /**
     * Line 61
     */
    public void deleteObject(String storageName, String objectName) throws InvalidKeyException,
            InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException, XmlParserException,
            ErrorResponseException, InternalException, IllegalArgumentException, InvalidResponseException, IOException {
        this.removeObject(storageName, objectName);
    }

    /**
     * Line 67, 71
     */
    public boolean doesObjectExist(String storageName, String key) throws InvalidKeyException,
            InvalidBucketNameException, NoSuchAlgorithmException, InsufficientDataException, XmlParserException,
            InternalException, InvalidResponseException, IllegalArgumentException, IOException, ErrorResponseException {
        try {
            this.statObject(storageName, key);
            return true;
        } catch (ErrorResponseException e) {
            if (e.errorResponse().errorCode() == ErrorCode.NO_SUCH_OBJECT)
                return false;
            throw e;
        }
    }
}
