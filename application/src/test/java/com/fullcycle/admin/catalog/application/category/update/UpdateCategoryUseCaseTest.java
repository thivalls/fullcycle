package com.fullcycle.admin.catalog.application.category.update;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.validation.handler.Notification;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UpdateCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private DefaultUpdateCategoryUseCase useCase;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    @DisplayName("Should update a category with valid params")
    public void test1() {
        final var origianlCategory = Category.newCategory("Film", "A categoria mais", true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

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
                        && Objects.isNull(updatedCategory.getDeletedAt())
        ));
    }

    @Test
    @DisplayName("Should throw error when update a category with invalid params")
    public void test2() {
        final var origianlCategory = Category.newCategory("Filmes", "A categoria mais assistida", true);

        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "The field {name} must not be null";
        final var expectedErrorsCount = 1;

        final var expectedId = origianlCategory.getId();

        final var updateCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(origianlCategory));

        Notification notification = useCase.execute(updateCommand).getLeft();

        // then
        Assertions.assertEquals(expectedErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorsCount, notification.getErrors().size());

        verify(categoryGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(0)).update(any());
    }

    @Test
    @DisplayName("Should update a category when change category from active to inactive")
    public void test3() {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;

        final var origianlCategory = Category.newCategory(expectedName, expectedDescription, true);

        final var expectedId = origianlCategory.getId();

        // when
        final var updateCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(origianlCategory.clone()));

        when(categoryGateway.update(any()))
                .thenAnswer(returnsFirstArg());

        Assertions.assertNull(origianlCategory.getDeletedAt());
        Assertions.assertTrue(origianlCategory.isActive());

        UpdateCategoryOutput updateCategoryOutput = useCase.execute(updateCommand).get();

        // then
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

    @Test
    @DisplayName("Should return error from gateway")
    public void test4() {
        final var origianlCategory = Category.newCategory("Film", "A categoria mais", true);

        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIllegalExeceptionErrorMessage = "Gateway error";
        final var expectedIsActive = true;
        final var expectedErrorsCount = 1;

        final var expectedId = origianlCategory.getId();

        final var updateCommand = UpdateCategoryCommand.with(expectedId.getValue(), expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(origianlCategory.clone()));

        when(categoryGateway.update(any()))
                .thenThrow(new IllegalStateException(expectedIllegalExeceptionErrorMessage));

        final var notification = useCase.execute(updateCommand).getLeft();

        Assertions.assertEquals(expectedIllegalExeceptionErrorMessage, notification.firstError().message());
        Assertions.assertEquals(expectedErrorsCount, notification.getErrors().size());

        // then
        verify(categoryGateway, times(1)).findById(eq(expectedId));
        verify(categoryGateway, times(1)).update(argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.isNull(category.getDeletedAt())
        ));
    }

    @Test
    @DisplayName("Should not update a category when ID is invalid")
    public void test5() {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedId = UUID.randomUUID().toString();
        final var expectedIllegalExeceptionErrorMessage = "Category with ID %s was not found".formatted(expectedId);
        final var expectedErrorsCount = 1;

        // when
        final var updateCommand = UpdateCategoryCommand.with(expectedId, expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.findById(any()))
                .thenReturn(Optional.empty());

        DomainException domainException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(updateCommand));

        // then
        Assertions.assertEquals(expectedIllegalExeceptionErrorMessage, domainException.firstError().message());
        Assertions.assertEquals(expectedErrorsCount, domainException.getErrors().size());
        verify(categoryGateway, times(1)).findById(eq(CategoryID.from(expectedId)));
        verify(categoryGateway, times(0)).update(any());
    }
}