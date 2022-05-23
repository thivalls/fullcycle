package com.fullcycle.admin.catalog.application.category.update;

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
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseTest {
    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @Mock
    private CategoryGateway categoryGateway;

    @Test
    @DisplayName("Should updata a category wity correct data")
    void test1() {
        final var category =
                Category.newCategory("beforeName", "beforeDescription", true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var expectedId = category.getId();

        final var command =
                UpdateCategoryCommand.with(
                        expectedId.getValue(),
                        expectedName,
                        expectedDescription,
                        expectedIsActive
                );

        when(categoryGateway.findyById(eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(command).get();

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        Mockito.verify(categoryGateway, times(1)).findyById(eq(expectedId));

        Mockito.verify(categoryGateway, times(1)).update(argThat(updatedCategory ->
                Objects.equals(expectedName, updatedCategory.getName())
                        && Objects.equals(expectedDescription, updatedCategory.getDescription())
                        && Objects.equals(expectedIsActive, updatedCategory.getActive())
                        && Objects.equals(expectedId, updatedCategory.getId())
                        && Objects.equals(category.getCreatedAt(), updatedCategory.getCreatedAt())
                        && category.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt())
                        && Objects.isNull(updatedCategory.getDeletedAt())
        ));
    }
}