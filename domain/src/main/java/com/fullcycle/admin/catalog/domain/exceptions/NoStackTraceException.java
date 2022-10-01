package com.fullcycle.admin.catalog.domain.exceptions;

public class NoStackTraceException extends RuntimeException {
    public NoStackTraceException(final String message) {
        this(message, null);
    }

    public NoStackTraceException(String message, Throwable cause) {
        super(message, cause, true, false);
    }
}
