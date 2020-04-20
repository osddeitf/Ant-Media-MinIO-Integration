package com.amazonaws.auth;

public interface AWSCredentials {
    String getAccessKey();
    String getSecretKey();
}