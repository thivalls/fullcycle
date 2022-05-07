package com.fullcycle.admin.catalog.domain.category;

import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;
import com.fullcycle.admin.catalog.domain.validation.Validator;

public class CategoryValidator extends Validator {
    private final Category category;

    public CategoryValidator(final Category category, ValidationHandler handler) {
        super(handler);
        this.category = category;
    }

    @Override
    public void validate() {
        if(this.category.getName() == null) {
            this.validationHandler().append(new Error("'name' must not be null"));
        }

        if(this.category.getName().isEmpty()) {
            this.validationHandler().append(new Error("'name' must not be empty"));
        }
    }
}
