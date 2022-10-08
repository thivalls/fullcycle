package com.fullcycle.admin.catalog.application.category.update;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.validation.Error;
import com.fullcycle.admin.catalog.domain.validation.handler.Notification;
import io.vavr.API;
import io.vavr.control.Either;

import java.util.Objects;
import java.util.function.Supplier;

import static io.vavr.API.Left;

public class DefaultUpdateCategoryUseCase extends UpdateCategoryUseCase {
    private CategoryGateway categoryGateway;

    public DefaultUpdateCategoryUseCase(final CategoryGateway categoryGateway) {
        this.categoryGateway =  Objects.requireNonNull(categoryGateway);
    }

    @Override
    public Either<Notification, UpdateCategoryOutput> execute(final UpdateCategoryCommand input) {
        Notification notification = Notification.create();

        CategoryID id = CategoryID.from(input.id());

        final var category = this.categoryGateway.findById(id).orElseThrow(notFound(input));

        category
                .update(input.name(), input.description(), input.isActive())
                .validate(notification);

        return notification.hasError() ? Left(notification) : update(category);
    }

    private Supplier<DomainException> notFound(UpdateCategoryCommand input) {
        return () -> DomainException.with(new Error("Category with ID %s was not found".formatted(input.id())));
    }

    private Either<Notification, UpdateCategoryOutput> update(final Category category) {
        return API.Try(() -> categoryGateway.update(category))
                .toEither()
                .bimap(Notification::create, UpdateCategoryOutput::from);
    }
}
