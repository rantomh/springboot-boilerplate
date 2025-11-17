package com.rantomah.boilerplate.core.exception;

import lombok.Getter;

@Getter
public class ValidationException extends GenericException {

    public static final String GENERAL_VALIDATION_ERROR = "Validation Error: %s";
    public static final String FIELD_VALIDATION_ERROR =
            "Validation Error: Field '%s' with value '%s' is invalid - %s";

    private final String field;
    private final transient Object rejectedValue;

    public ValidationException(String errorCode, String message) {
        super(errorCode, message);
        this.field = null;
        this.rejectedValue = null;
    }

    public ValidationException(
            String errorCode, String message, String field, Object rejectedValue) {
        super(errorCode, message);
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    public ValidationException(String errorCode, Throwable cause) {
        super(errorCode, cause.getMessage());
        this.field = null;
        this.rejectedValue = null;
    }

    public ValidationException(
            String errorCode, String field, Object rejectedValue, Throwable cause) {
        super(errorCode, cause.getMessage());
        this.field = field;
        this.rejectedValue = rejectedValue;
    }

    @Override
    public String getMessage() {
        if (getField() != null && getRejectedValue() != null) {
            return String.format(
                    FIELD_VALIDATION_ERROR, getField(), getRejectedValue(), super.getMessage());
        }
        return String.format(GENERAL_VALIDATION_ERROR, super.getMessage());
    }
}
