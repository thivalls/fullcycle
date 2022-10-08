package com.fullcycle.admin.catalog.application.category.update;

import com.fullcycle.admin.catalog.domain.category.CategoryID;

public record UpdateCategoryCommand(
        String id,
        String name,
        String description,
        boolean isActive
) {
    public static UpdateCategoryCommand with(
            final String id,
            final String name,
            final String description,
            final Boolean isActive
    ) {
        return new UpdateCategoryCommand(id, name, description, isActive);
    }
}
