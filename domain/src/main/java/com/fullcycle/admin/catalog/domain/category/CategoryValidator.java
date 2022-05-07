package com.fullcycle.admin.catalog.domain.category;

import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {
    public static final int NAME_MIN_LENGTH = 3;
    public static final int NAME_MAX_LENGTH = 255;
    private final Category category;

    public CategoryValidator(final Category category, ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        checkNameConstraints();
    }

    private void checkNameConstraints() {
        final var name = this.category.getName();
        if(this.category.getName() == null) {
            this.validationHandler().append(new Error("'name' must not be null"));
            return;
        }

        if(this.category.getName().isBlank()) {
            this.validationHandler().append(new Error("'name' must not be empty"));
            return;
        }

        final int size = name.trim().length();
        if(size < NAME_MIN_LENGTH || size > NAME_MAX_LENGTH) {
            this.validationHandler().append(new Error("'name' size must be between 3 and 255"));
        }
    }
}
