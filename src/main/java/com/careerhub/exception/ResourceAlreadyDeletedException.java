package com.careerhub.exception;

public class ResourceAlreadyDeletedException extends RuntimeException {

    public ResourceAlreadyDeletedException(String message) {
        super(message);
    }

    public ResourceAlreadyDeletedException(String message, Throwable cause) {
        super(message, cause);
    }
}

