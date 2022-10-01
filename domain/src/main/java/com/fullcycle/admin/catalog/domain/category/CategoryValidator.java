package com.fullcycle.admin.catalog.domain.category;

import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    public static final int MIN_LENGTH_CATEGORY_NAME = 3;
    public static final int MAX_LENGTH_CATEGORY_NAME = 255;

    private final Category category;

    public CategoryValidator(final Category category, ValidationHandler validationHandler) {
        super(validationHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        final var name = this.category.getName();
        if(name == null) {
            validationHandler().append(new Error("The field {name} must not be null"));
            return;
        }

        if(name.isBlank()) {
            validationHandler().append(new Error("The field {name} must not be empty"));
            return;
        }

        final var length = this.category.getName().trim().length();
        if(length < MIN_LENGTH_CATEGORY_NAME || length > MAX_LENGTH_CATEGORY_NAME) {
            validationHandler().append(new Error("The field {name} must be between 3 and 255 characters"));
        }
    }
}
