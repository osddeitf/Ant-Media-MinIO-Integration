package com.amazonaws.services.s3.transfer;

import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.model.PutObjectRequest;

public class TransferManager {

    private AmazonS3 client;
    
    public TransferManager(AmazonS3 client) {
        this.client = client;
    }

    public Upload upload(PutObjectRequest object) {
        return new UploadImpl(client, object);
    }
}
