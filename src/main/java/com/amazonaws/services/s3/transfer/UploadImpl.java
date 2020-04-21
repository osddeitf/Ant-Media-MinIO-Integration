package com.amazonaws.services.s3.transfer;

import java.io.FileInputStream;
import java.io.InputStream;

import com.amazonaws.AmazonServiceException;
import com.amazonaws.event.ProgressEvent;
import com.amazonaws.event.ProgressEventType;
import com.amazonaws.event.ProgressListener;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

import io.minio.PutObjectOptions;
import io.minio.errors.ErrorResponseException;

public class UploadImpl implements Upload {

    private final AmazonS3 client;
    private final PutObjectRequest object;
    private ProgressListener listener;

    public UploadImpl(AmazonS3 client, PutObjectRequest object) {
        this.client = client;
        this.object = object;
    }

    private void emit(ProgressEventType type) {
        listener.progressChanged(new ProgressEvent(type));
    }

    @Override
    public void addProgressListener(ProgressListener listener) {
        this.listener = listener;
    }

    @Override
    public void waitForCompletion() throws Exception {
        try {
            InputStream inputStream = new FileInputStream(object.getFile());
            this.client.putObject(
                object.getStorageName(),
                object.getKey(),
                inputStream,
                new PutObjectOptions(inputStream.available(), 10 * 1024 * 1024)
            );
            emit(ProgressEventType.TRANSFER_COMPLETED_EVENT);
        }
        catch (ErrorResponseException exception) {
            emit(ProgressEventType.TRANSFER_FAILED_EVENT);
            throw new AmazonServiceException(exception);
        }
        catch (Exception e) {
            emit(ProgressEventType.TRANSFER_FAILED_EVENT);
            throw e;
        }
    }
}
