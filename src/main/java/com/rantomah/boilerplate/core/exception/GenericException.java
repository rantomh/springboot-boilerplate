package com.rantomah.boilerplate.core.exception;

import lombok.Getter;

@Getter
public class GenericException extends RuntimeException {

    protected final String code;

    protected final Integer httpStatus;

    protected final Integer status;

    protected final String message;

    public GenericException() {
        super(ApiError.GENERAL_ERROR.message());
        this.code = ApiError.GENERAL_ERROR.code();
        this.httpStatus = ApiError.GENERAL_ERROR.httpStatus();
        this.status = ApiError.GENERAL_ERROR.status();
        this.message = ApiError.GENERAL_ERROR.message();
    }

    public GenericException(String message) {
        super(message);
        this.code = ApiError.GENERAL_ERROR.code();
        this.httpStatus = ApiError.GENERAL_ERROR.httpStatus();
        this.status = ApiError.GENERAL_ERROR.status();
        this.message = message;
    }

    public GenericException(ApiError error) {
        super(error.message());
        this.code = error.code();
        this.httpStatus = error.httpStatus();
        this.status = error.status();
        this.message = error.message();
    }

    public GenericException(ApiError error, String message) {
        super(message);
        this.code = error.code();
        this.httpStatus = error.httpStatus();
        this.status = error.status();
        this.message = message;
    }

    public GenericException(String code, String message) {
        super(message);
        this.code = code;
        this.httpStatus = ApiError.GENERAL_ERROR.httpStatus();
        this.status = null;
        this.message = message;
    }
}
