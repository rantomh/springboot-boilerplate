package com.rantomah.boilerplate.core.exception;

public class UserLockedException extends AuthenticationException {

    public UserLockedException() {
        super(ApiError.USER_LOCKED);
    }
}
