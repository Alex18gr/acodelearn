package com.alexc.acodelearn.resourceserver.rest;

public class UserNotAllowedException extends RuntimeException {

    public UserNotAllowedException() {
        super();
    }

    public UserNotAllowedException(String message) {
        super(message);
    }

    public UserNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public UserNotAllowedException(Throwable cause) {
        super(cause);
    }

    protected UserNotAllowedException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
