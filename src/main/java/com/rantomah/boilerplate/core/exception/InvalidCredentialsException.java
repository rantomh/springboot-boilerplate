package com.rantomah.boilerplate.core.exception;

public class InvalidCredentialsException extends GenericException {

    public InvalidCredentialsException() {
        super(ApiError.INVALID_CREDENTIALS);
    }

    public InvalidCredentialsException(String message) {
        super(ApiError.INVALID_CREDENTIALS, message);
    }
}
