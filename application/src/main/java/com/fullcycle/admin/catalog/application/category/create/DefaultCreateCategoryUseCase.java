package com.fullcycle.admin.catalog.application.category.create;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.validation.handlers.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;

import static io.vavr.API.*;

public class DefaultCreateCategoryUseCase extends CreateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultCreateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, CreateCategoryOutput> execute(final CreateCategoryCommand createCategoryCommand) {
        final var name = createCategoryCommand.name();
        final var description = createCategoryCommand.description();
        final var isActive = createCategoryCommand.isActive();

        Notification notification = Notification.create();

        final var category = Category.newCategory(name, description, isActive);
        category.validate(notification);

        return notification.hasError() ? Left(notification) : create(category);
    }

    private Either<Notification, CreateCategoryOutput> create(Category category) {
        return Try(() -> this.categoryGateway.create(category))
                .toEither()
                .bimap(Notification::create, CreateCategoryOutput::from);
    }
}
