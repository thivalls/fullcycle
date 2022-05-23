package com.fullcycle.admin.catalog.application.category.update;

import com.fullcycle.admin.catalog.application.category.create.CreateCategoryOutput;
import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.handlers.Notification;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;
import static io.vavr.API.Try;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {
    private final CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(CategoryGateway categoryGateway) {
        this.categoryGateway = Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(UpdateCategoryCommand updateCategoryCommand) {

        final CategoryID id = CategoryID.from(updateCategoryCommand.id());
        final var name = updateCategoryCommand.name();
        final var description = updateCategoryCommand.description();
        final var isActive = updateCategoryCommand.isActive();

        Notification notification = Notification.create();

        final var category = categoryGateway.findyById(id).orElseThrow(notFound(id));

        category.update(name, description, isActive).validate(notification);

        return notification.hasError() ? Left(notification) : update(category);
    }

    private Supplier<DomainException> notFound(CategoryID id) {
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(id.getValue())));
    }

    private Either<Notification, UpdateCategoryOutput> update(Category category) {
        return Try(() -> this.categoryGateway.update(category))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }
}
