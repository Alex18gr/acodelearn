package com.alexc.acodelearn.resourceserver.rest;

public class ContentNotAllowedException extends RuntimeException {
    public ContentNotAllowedException() {
        super();
    }

    public ContentNotAllowedException(String message) {
        super(message);
    }

    public ContentNotAllowedException(String message, Throwable cause) {
        super(message, cause);
    }

    public ContentNotAllowedException(Throwable cause) {
        super(cause);
    }

    protected ContentNotAllowedException(String message, Throwable cause, boolean enableSuppression,
                                         boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }
}
