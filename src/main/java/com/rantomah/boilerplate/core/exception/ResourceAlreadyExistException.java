package com.rantomah.boilerplate.core.exception;

public class ResourceAlreadyExistException extends GenericException {

    public ResourceAlreadyExistException() {
        super(ApiError.RESOURCE_ALREADY_EXIST);
    }

    public ResourceAlreadyExistException(String message) {
        super(ApiError.RESOURCE_ALREADY_EXIST, message);
    }
}
