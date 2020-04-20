package com.amazonaws.services.s3.transfer;

import com.amazonaws.services.s3.AmazonS3;

public class TransferManagerBuilder {

    private TransferManagerBuilder() {}

    private static TransferManagerBuilder builder;

    public static TransferManagerBuilder standard() {
        if (builder == null)
            builder = new TransferManagerBuilder();
        return builder;
    }


    private AmazonS3 client;

    public TransferManagerBuilder withS3Client(AmazonS3 s3) {
        client = s3;
        return this;
    }

    public TransferManager build() {
        return new TransferManager(client);
    }
}
