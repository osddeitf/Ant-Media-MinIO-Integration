package com.amazonaws.auth;

public class BasicAWSCredentials implements AWSCredentials {

    private final String accessKey;
    private final String secretKey;

    public BasicAWSCredentials(String accessKey, String secretKey) {
        this.accessKey = accessKey;
        this.secretKey = secretKey;
    }

    @Override
    public String getAccessKey() { return accessKey; }
    @Override
    public String getSecretKey() { return secretKey; }
}
