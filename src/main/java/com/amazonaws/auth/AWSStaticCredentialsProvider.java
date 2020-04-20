package com.amazonaws.auth;

public class AWSStaticCredentialsProvider implements AWSCredentialsProvider {

    private AWSCredentials credentials;

    public AWSStaticCredentialsProvider(AWSCredentials credentials) {
        this.credentials = credentials;
    }

    @Override
    public AWSCredentials getCredentials() {
        return credentials;
    }
}