package com.amazonaws;

import io.minio.errors.ErrorResponseException;

public class AmazonServiceException extends ErrorResponseException {
    private static final long serialVersionUID = 1L;

    private final ErrorResponseException instance;

	public AmazonServiceException(ErrorResponseException exception) {
        super(exception.errorResponse(), null);
        instance = exception;
    }

    @Override
    public String toString() {
        return instance.toString();
    }
}
