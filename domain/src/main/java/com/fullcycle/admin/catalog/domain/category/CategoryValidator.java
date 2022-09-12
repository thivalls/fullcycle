package com.fullcycle.admin.catalog.domain.category;

import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {

    private final Category category;

    public CategoryValidator(final Category category, ValidationHandler validationHandler) {
        super(validationHandler);
        this.category = category;
    }

    @Override
    public void validate() {
        if(this.category.getName() == null) {
            validationHandler().append(new Error("The field {name} must not be null"));
        }

        if(this.category.getName().length() < 3) {
            validationHandler().append(new Error("The field {name} must have at least 3 characters"));
        }
    }
}
