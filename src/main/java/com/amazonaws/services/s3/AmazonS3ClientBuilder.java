package com.amazonaws.services.s3;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.client.builder.AwsClientBuilder;
import com.amazonaws.regions.Regions;

import io.minio.errors.InvalidEndpointException;
import io.minio.errors.InvalidPortException;
// com.amazonaws.services.s3.AmazonS3ClientBuilder.withCredentials
// Lcom/amazonaws/auth/AWSCredentialsProvider;
// AwsClientBuilder
public class AmazonS3ClientBuilder extends AwsClientBuilder {

    private AmazonS3ClientBuilder() {}

    private static AmazonS3ClientBuilder builder;

    public static AmazonS3ClientBuilder standard() {
        if (builder == null)
            builder = new AmazonS3ClientBuilder();
        return builder;
    }

    private AWSCredentials credentials;
    private Regions regions;
    private ClientConfiguration config;

    public AwsClientBuilder withCredentials(AWSCredentialsProvider credentialsProvider) {
        this.credentials = credentialsProvider.getCredentials();
        return this;
    }

    public AwsClientBuilder withRegion(Regions regions) {
        this.regions = regions;
        return this;
    }

    // @Override
    public Object build() {
        /* Tricks */
        try {
            return new AmazonS3(
                regions.getEndpoint(),
                credentials.getAccessKey(),
                credentials.getSecretKey(),
                true
            );
        } catch (InvalidEndpointException | InvalidPortException e) {
            // original not throw
            e.printStackTrace();
            return null;
        }
    }
}
