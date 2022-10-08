package com.fullcycle.admin.catalog.application.category.create;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.validation.handler.Notification;
import com.fullcycle.admin.catalog.domain.validation.handler.ThrowsValidationHandler;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.Left;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase{

    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(CreateCategoryCommand input) {
        final var notification = Notification.create();
        final var category = Category.newCategory(input.name(), input.description(), input.isActive());
        category.validate(notification);
        return notification.hasError() ? Left(notification) : create(category);
    }

    private Either<Notification, CreateCategoryOutput> create(final Category category) {
        return API.Try(() -> categoryGateway.create(category))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
