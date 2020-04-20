package com.amazonaws.services.s3.transfer;

import java.io.FileInputStream;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;

import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressEventType;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import io.minio.PutObjectOptions;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.InsufficientDataException;
import io.minio.errors.InternalException;
import io.minio.errors.InvalidBucketNameException;
import io.minio.errors.InvalidResponseException;
import io.minio.errors.XmlParserException;

public class UploadImpl implements Upload {

    private AmazonS3 client;
    private PutObjectRequest object;
    private ProgressListener listener;

    public UploadImpl(AmazonS3 client, PutObjectRequest object) {
        this.client = client;
        this.object = object;
    }

    @Override
    public void addProgressListener(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public void waitForCompletion() {
        try {
            String a = object.getStorageName();
            String b = object.getKey();
            FileInputStream c = new FileInputStream(object.getFile());
            PutObjectOptions d = new PutObjectOptions(c.available(), 10 * 1024 * 1024);
            this.client.putObject(a, b, c, d);
            listener.progressChanged(new ProgressEvent(ProgressEventType.TRANSFER_COMPLETED_EVENT));
        } catch (InvalidKeyException | InvalidBucketNameException | NoSuchAlgorithmException | XmlParserException
                | ErrorResponseException | InternalException | IllegalArgumentException | InsufficientDataException
                | InvalidResponseException | IOException e) {
            listener.progressChanged(new ProgressEvent(ProgressEventType.TRANSFER_FAILED_EVENT));
        }
    }
}
