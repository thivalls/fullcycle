package com.fullcycle.admin.catalog.application.category.create;

import com.fullcycle.admin.catalog.domain.category.Category;

public record CreateCategoryCommand(
        String name, String description, boolean isActive
) {
    public static CreateCategoryCommand with(final String name, final String description, final Boolean isActive) {
        return new CreateCategoryCommand(name, description, isActive);
    }
}
