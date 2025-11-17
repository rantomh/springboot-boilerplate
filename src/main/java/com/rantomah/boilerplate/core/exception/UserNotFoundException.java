package com.rantomah.boilerplate.core.exception;

public class UserNotFoundException extends AuthenticationException {

    public UserNotFoundException() {
        super(ApiError.USER_NOT_FOUND);
    }
}
