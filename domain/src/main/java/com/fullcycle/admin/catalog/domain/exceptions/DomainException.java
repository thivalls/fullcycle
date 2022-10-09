package com.fullcycle.admin.catalog.domain.exceptions;

import com.fullcycle.admin.catalog.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStackTraceException {
    private final List<Error> errors;

    private DomainException(String message, final List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(final Error error) {
        return new DomainException(error.message(), List.of(error));
    }

    public static DomainException with(final List<Error> errors) {
        return new DomainException("", errors);
    }

    public List<Error> getErrors() {
        return errors;
    }

    public Error firstError() {
        if(getErrors() != null && !getErrors().isEmpty()) {
            return getErrors().get(0);
        } else {
            return null;
        }
    }
}
