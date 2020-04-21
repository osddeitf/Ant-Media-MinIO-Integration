package com.amazonaws.client.builder;

import com.amazonaws.ClientConfiguration;
import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSCredentialsProvider;
import com.amazonaws.regions.Regions;

public abstract class AwsClientBuilder {

    protected AWSCredentials credentials;
    protected Regions regions;

    public AwsClientBuilder withClientConfiguration(ClientConfiguration config) {
        return this;
    }

    public AwsClientBuilder withCredentials(AWSCredentialsProvider credentialsProvider) {
        this.credentials = credentialsProvider.getCredentials();
        return this;
    }

    public AwsClientBuilder withRegion(Regions regions) {
        this.regions = regions;
        return this;
    }

    public abstract Object build() throws Exception;
}