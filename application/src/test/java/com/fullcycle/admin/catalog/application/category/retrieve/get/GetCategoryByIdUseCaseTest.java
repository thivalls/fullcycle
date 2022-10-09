package com.fullcycle.admin.catalog.application.category.retrieve.get;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class GetCategoryByIdUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private DefaultGetCategoryByIdByIdUseCase useCase;

    @BeforeEach
    void cleanUp() {
        reset(categoryGateway);
    }

    @Test
    @DisplayName("Should be possible find a category by a valid ID")
    void test1() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Os filmes mais assistidos";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = category.getId();

        Mockito.when(categoryGateway.findById(eq(expectedId)))
                .thenReturn(Optional.of(category.clone()));

        final var categoryFound = useCase.execute(expectedId.getValue());

        verify(categoryGateway, times(1)).findById(any());

        Assertions.assertEquals(CategoryOutput.from(category), categoryFound);
        Assertions.assertEquals(expectedName, categoryFound.name());
        Assertions.assertEquals(expectedDescription, categoryFound.description());
        Assertions.assertEquals(expectedIsActive, categoryFound.isActive());
        Assertions.assertNotNull(categoryFound.id());
        Assertions.assertEquals(expectedId, categoryFound.id());
        Assertions.assertNotNull(categoryFound.createdAt());
        Assertions.assertEquals(category.getCreatedAt(), categoryFound.createdAt());
        Assertions.assertNull(categoryFound.deletedAt());
        Assertions.assertEquals(category.getDeletedAt(), categoryFound.deletedAt());
    }

    @Test
    @DisplayName("Should throw error when it tries finding a category with invalid ID")
    void test2() {
        final var expectedErrorCount = 1;
        final var expectedId = "qualquer id";
        final var expectedErrorMessage = "Category with ID %s was not found".formatted(expectedId);

        Mockito.when(categoryGateway.findById(eq(CategoryID.from(expectedId))))
                .thenReturn(Optional.empty());

        final var domainException = Assertions.assertThrows(
                DomainException.class,
                () -> useCase.execute(expectedId)
        );

        verify(categoryGateway, times(1)).findById(any());

        Assertions.assertEquals(expectedErrorMessage, domainException.getMessage());
        Assertions.assertEquals(expectedErrorCount, domainException.getErrors().size());
    }

    @Test
    @DisplayName("Should throw error when gateway produces some internal error")
    void test3() {
        final var expectedErrorMessage = "Gateway error";
        final var expectedId = "qualquer id";

        Mockito.when(categoryGateway.findById(eq(CategoryID.from(expectedId))))
                .thenThrow(new IllegalStateException(expectedErrorMessage));

        final var domainException = Assertions.assertThrows(
                IllegalStateException.class,
                () -> useCase.execute(expectedId)
        );

        verify(categoryGateway, times(1)).findById(any());

        Assertions.assertEquals(expectedErrorMessage, domainException.getMessage());
    }
}
