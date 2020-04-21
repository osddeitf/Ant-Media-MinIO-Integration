package com.amazonaws.services.s3;

import com.amazonaws.client.builder.AwsClientBuilder;
import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;

public class AmazonS3ClientBuilder extends AwsClientBuilder {

    private AmazonS3ClientBuilder() {}

    private static AmazonS3ClientBuilder builder;

    public static AmazonS3ClientBuilder standard() {
        if (builder == null)
            builder = new AmazonS3ClientBuilder();
        return builder;
    }

    @Override
    public Object build() throws InvalidEndpointException, InvalidPortException {
        return new AmazonS3(
            regions.getEndpoint(),
            credentials.getAccessKey(),
            credentials.getSecretKey(),
            true
        );
    }
}
