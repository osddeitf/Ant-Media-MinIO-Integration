package com.amazonaws.client.builder;

import com.amazonaws.ClientConfiguration;

public abstract class AwsClientBuilder {
    public AwsClientBuilder withClientConfiguration(ClientConfiguration config) {
        return this;
    }
}