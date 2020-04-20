package com.amazonaws.services.s3.transfer;

import com.amazonaws.event.ProgressListener;

public interface Upload {
    // private AmazonS3 client;
    // private PutObjectRequest object;
    // private ProgressListener listener;

    void addProgressListener(ProgressListener listener);

    void waitForCompletion();
}
