package com.fullcycle.admin.catalog.application.category.create;

import com.fullcycle.admin.catalog.application.UseCase;
import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalog.domain.pagination.Pagination;
import com.fullcycle.admin.catalog.domain.validation.handlers.Notification;
import io.vavr.control.Either;

import java.util.Optional;

public abstract class CreateCategoryUseCase extends UseCase<CreateCategoryCommand, Either<Notification,CreateCategoryOutput>> {

}
