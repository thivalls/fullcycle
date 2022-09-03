package com.fullcycle.admin.catalog.domain.validation;

public abstract class Validator {

    private final ValidationHandler handler;

    protected Validator(final ValidationHandler validationHandler) {
        this.handler = validationHandler;
    }

    protected ValidationHandler validationHandler() {
        return this.handler;
    }

    public abstract void validate();
}
