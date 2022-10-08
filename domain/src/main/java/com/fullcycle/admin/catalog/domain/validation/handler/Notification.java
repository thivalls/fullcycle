package com.fullcycle.admin.catalog.domain.validation.handler;

import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;

import java.util.ArrayList;
import java.util.List;

public class Notification implements ValidationHandler {

    private final List<Error> errors;

    private Notification(final List<Error> errors) {
        this.errors = errors;
    }

    public static Notification create() {
        return new Notification(new ArrayList<>());
    }

    public static Notification create(final Throwable t) {
        return create(new Error(t.getMessage()));
    }

    public static Notification create(final Error error) {
        return new Notification(new ArrayList<>()).append(error);
    }

    @Override
    public Notification append(final Error error) {
        this.errors.add(error);
        return this;
    }

    @Override
    public Notification append(final ValidationHandler validationHandler) {
        this.errors.addAll(validationHandler.getErrors());
        return this;
    }

    @Override
    public Notification validate(final Validation validation) {

        try {
            validation.validate();
        } catch (DomainException e) {
            this.errors.addAll(e.getErrors());
        }catch (Throwable t) {
            this.errors.add(new Error(t.getMessage()));
        }

        return this;
    }

    @Override
    public List<Error> getErrors() {
        return errors;
    }

    @Override
    public boolean hasError() {
        return ValidationHandler.super.hasError();
    }
}
