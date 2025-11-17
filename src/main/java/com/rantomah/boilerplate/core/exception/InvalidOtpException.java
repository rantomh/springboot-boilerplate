package com.rantomah.boilerplate.core.exception;

public class InvalidOtpException extends GenericException {

    public InvalidOtpException() {
        super(ApiError.INVALID_OTP);
    }

    public InvalidOtpException(ApiError error) {
        super(error);
    }

    public InvalidOtpException(String message) {
        super(ApiError.INVALID_OTP, message);
    }
}
