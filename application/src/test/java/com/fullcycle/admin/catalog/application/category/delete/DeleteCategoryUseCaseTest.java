package com.fullcycle.admin.catalog.application.category.delete;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class DeleteCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private DefaultDeleteCategoryUseCase useCase;

    @BeforeEach
    void cleanUp() {
        Mockito.reset(categoryGateway);
    }

    @Test
    @DisplayName("Should be possible to delete some category")
    public void test1() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Os filmes mais assistidos";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = category.getId();

        doNothing()
                .when(categoryGateway).deleteById(eq(expectedId));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId.getValue()));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }

    @Test
    @DisplayName("Should throw error when it tries deleting a category with invalid ID")
    public void test2() {
        final var expectedId = "qualquer id";

        doNothing()
                .when(categoryGateway).deleteById(eq(CategoryID.from(expectedId)));

        Assertions.assertDoesNotThrow(() -> useCase.execute(expectedId));

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(CategoryID.from(expectedId)));
    }

    @Test
    @DisplayName("Should throw error when gateway produces some error")
    public void test3() {
        final var expectedName = "Filmes";
        final var expectedDescription = "Os filmes mais assistidos";
        final var expectedGatewayErrorMessage = "Gateway error";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var expectedId = category.getId();

        doThrow(new IllegalStateException("Gateway error"))
                .when(categoryGateway).deleteById(eq(expectedId));

        IllegalStateException illegalStateException = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(expectedId.getValue()));

        Assertions.assertEquals(expectedGatewayErrorMessage, illegalStateException.getMessage());

        Mockito.verify(categoryGateway, times(1)).deleteById(eq(expectedId));
    }
}
