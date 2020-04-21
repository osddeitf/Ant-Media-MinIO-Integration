package com.amazonaws;

import io.minio.errors.ErrorResponseException;
import io.minio.messages.ErrorResponse;

public class AmazonServiceException extends ErrorResponseException {
    private static final long serialVersionUID = 1L;

    private final ErrorResponseException instance;

	public AmazonServiceException(ErrorResponseException errorResponse) {
        super(null, null);
        instance = errorResponse;
    }

    @Override
    public ErrorResponse errorResponse() {
        return instance.errorResponse();
    }

    @Override
    public String toString() {
        return instance.toString();
    }
}
