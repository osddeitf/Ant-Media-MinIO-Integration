package com.amazonaws;

public class ClientConfiguration {

    public ClientConfiguration withMaxConnections(int connections) {
        return this;
    }

    public ClientConfiguration withConnectionTimeout(int timeout) {
        return this;
    }

    public ClientConfiguration withMaxErrorRetry(int retries) {
        return this;
    }
}
