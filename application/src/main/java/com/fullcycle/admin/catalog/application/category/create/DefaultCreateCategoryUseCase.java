package com.fullcycle.admin.catalog.application.category.create;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.validation.handlers.ThrowsValidationHandler;

import java.util.Objects;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public CreateCategoryOutput execute(final CreateCategoryCommand createCategoryCommand) {
        final var name = createCategoryCommand.name();
        final var description = createCategoryCommand.description();
        final var isActive = createCategoryCommand.isActive();

        final var category = Category.newCategory(name, description, isActive);
        category.validate(new ThrowsValidationHandler());

        return CreateCategoryOutput.from(this.categoryGateway.create(category));
    }
}
