package com.rantomah.boilerplate.core.exception;

public class UserDisabledException extends AuthenticationException {

    public UserDisabledException() {
        super(ApiError.USER_DISABLED);
    }
}
