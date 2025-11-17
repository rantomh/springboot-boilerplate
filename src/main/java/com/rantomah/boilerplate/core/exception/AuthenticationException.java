package com.rantomah.boilerplate.core.exception;

public class AuthenticationException extends GenericException {

    public AuthenticationException() {
        super(ApiError.AUTHENTICATION_ERROR);
    }

    public AuthenticationException(ApiError error) {
        super(error);
    }

    public AuthenticationException(String message) {
        super(ApiError.AUTHENTICATION_ERROR, message);
    }
}
