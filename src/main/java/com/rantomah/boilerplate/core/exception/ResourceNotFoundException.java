package com.rantomah.boilerplate.core.exception;

public class ResourceNotFoundException extends GenericException {

    public ResourceNotFoundException() {
        super(ApiError.RESOURCE_NOT_FOUND);
    }

    public ResourceNotFoundException(String message) {
        super(ApiError.RESOURCE_NOT_FOUND, message);
    }
}
