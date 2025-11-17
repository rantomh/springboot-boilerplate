package com.rantomah.boilerplate.core.exception;

public enum ApiError {
    AUTHENTICATION_ERROR("AUTHENTICATION_ERROR", 401, 4010, "error.default.authentication-error"),
    INVALID_CREDENTIALS("INVALID_CREDENTIALS", 401, 4011, "error.default.invalid-credentials"),
    INVALID_OTP("INVALID_OTP", 401, 4012, "error.default.invalid-credentials"),

    RESOURCE_NOT_FOUND("RESOURCE_NOT_FOUND", 404, 4040, "error.default.resource-not-found"),
    USER_NOT_FOUND("USER_NOT_FOUND", 404, 4041, "auth.login.user-not-found"),

    USER_DISABLED("USER_DISABLED", 403, 4031, "error.default.user-disabled"),
    USER_LOCKED("USER_LOCKED", 403, 4032, "error.default.user-locked"),
    USER_ALREADY_ENABLED("USER_ALREADY_ENABLED", 403, 4033, "error.default.user-already-enabled"),

    RESOURCE_ALREADY_EXIST(
            "RESOURCE_ALREADY_EXIST", 409, 4091, "error.default.resource-already-exist"),

    GENERAL_ERROR("GENERAL_ERROR", 500, 5000, "error.default.general-error"),

    CLOUDINARY_ERROR("CLOUDINARY_ERROR", 502, 5021, "error.default.cloudinary");

    private final String code;
    private final int httpStatus;
    private final int status;
    private final String message;

    private ApiError(String code, int httpStatus, int status, String message) {
        this.code = code;
        this.httpStatus = httpStatus;
        this.status = status;
        this.message = message;
    }

    public String code() {
        return code;
    }

    public int httpStatus() {
        return httpStatus;
    }

    public int status() {
        return status;
    }

    public String message() {
        return message;
    }
}
