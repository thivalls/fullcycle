package com.fullcycle.admin.catalog.application.category.update;

import com.fullcycle.admin.catalog.application.category.create.CreateCategoryCommand;
import com.fullcycle.admin.catalog.application.category.create.CreateCategoryUseCase;
import com.fullcycle.admin.catalog.application.category.create.DefaultCreateCategoryUseCase;
import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.argThat;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Test
    @DisplayName("Should update a category with valid params")
    public void test1() {
        final var origianlCategory = Category.newCategory("Film", "A categoria mais", true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var expectedId = origianlCategory.getId();

        final var updateCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(origianlCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        UpdateCategoryOutput updateCategoryOutput = useCase.execute(updateCommand).get();

        Assertions.assertNotNull(updateCategoryOutput);
        Assertions.assertNotNull(updateCategoryOutput.id());

        final var oldCreatedAt = origianlCategory.getCreatedAt();
        final var oldUpdatedAt = origianlCategory.getUpdatedAt();

        // then
        verify(categoryGateway, times(1)).findById(eq(expectedId));

        verify(categoryGateway, times(1)).update(argThat(updatedCategory ->
                Objects.equals(expectedName, updatedCategory.getName())
                        && Objects.equals(expectedDescription, updatedCategory.getDescription())
                        && Objects.equals(expectedIsActive, updatedCategory.isActive())
                        && Objects.nonNull(updatedCategory.getId())
                        && Objects.equals(expectedId, updatedCategory.getId())
                        && Objects.nonNull(updatedCategory.getCreatedAt())
                        && Objects.equals(oldCreatedAt, updatedCategory.getCreatedAt())
                        && Objects.nonNull(updatedCategory.getUpdatedAt())
                        && oldUpdatedAt.isBefore(updatedCategory.getUpdatedAt())
                        && Objects.nonNull(updatedCategory.getDeletedAt())
        ));
    }
}