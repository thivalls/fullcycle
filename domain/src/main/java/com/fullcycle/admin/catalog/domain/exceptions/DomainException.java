package com.fullcycle.admin.catalog.domain.exceptions;

import com.fullcycle.admin.catalog.domain.validation.Error;

import java.util.List;

public class DomainException extends NoStackTraceException{
    private final List<Error> errors;

    private DomainException(final String message, final List<Error> errors) {
        super(message);
        this.errors = errors;
    }

    public static DomainException with(Error error) {
        return new DomainException(error.message(), List.of(error));
    }

    public static DomainException with(List<Error> errors) {
        return new DomainException("", errors);
    }

    public List<Error> getErrors() {
        return errors;
    }
}
