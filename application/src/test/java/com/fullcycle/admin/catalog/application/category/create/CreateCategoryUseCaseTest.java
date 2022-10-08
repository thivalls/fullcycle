package com.fullcycle.admin.catalog.application.category.create;

import com.fullcycle.admin.catalog.domain.category.CategoryGateway;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Objects;

import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CreateCategoryUseCaseTest {

    @Mock
    private CategoryGateway categoryGateway;

    @InjectMocks
    private DefaultCreateCategoryUseCase useCase;

    /**
     * 1. Teste do caminho feliz
     * 2. Teste passando uma propriedade inválida (name)
     * 3. Teste criando uma categoria inativa
     * 4. Teste simulando um erro generico vindo do gateway
     */

    @Test
    @DisplayName("Should create a new category with valid params")
    public void test1() {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        // do
        final var comand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenAnswer(returnsFirstArg());

        final var actualOutput = useCase.execute(comand);

        Assertions.assertNotNull(actualOutput);
        Assertions.assertNotNull(actualOutput.id());

        // then
        verify(categoryGateway, times(1)).create(argThat(category ->
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
    @DisplayName("Should throws DomainException when to try creating a new category with invalid params")
    public void test2() {
        // given
        final String expectedName = null;
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedErrorMessage = "The field {name} must not be null";
        final var expectedErrorsCount = 1;

        // do
        final var comand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        // final var actualOutput = useCase.execute(comand);

        // then
        final var actualCategoryException = Assertions.assertThrows(DomainException.class, () -> useCase.execute(comand));
        Assertions.assertEquals(expectedErrorMessage, actualCategoryException.getMessage());
        Assertions.assertEquals(expectedErrorsCount, actualCategoryException.getErrors().size());

        verify(categoryGateway, times(0)).create(any());
    }

    @Test
    @DisplayName("Should create a new category with inactive category")
    public void test3() {
        // given
        final String expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = false;
        final var expectedErrorMessage = "The field {name} must not be null";
        final var expectedErrorsCount = 1;

        // do
        final var comand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any())).thenAnswer(returnsFirstArg());

        // then
        final var actualCategoryOutput = useCase.execute(comand);

        Assertions.assertNotNull(actualCategoryOutput);
        Assertions.assertNotNull(actualCategoryOutput.id());

        verify(categoryGateway, times(1)).create(argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.nonNull(category.getDeletedAt())
        ));
    }

    @Test
    @DisplayName("Should throws gateway error when some error happen in gateway")
    public void test4() {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;
        final var expectedIllegalExeceptionErrorMessage = "Gateway error";

        // do
        final var comand =
                CreateCategoryCommand.with(expectedName, expectedDescription, expectedIsActive);

        when(categoryGateway.create(any()))
                .thenThrow(new IllegalStateException(expectedIllegalExeceptionErrorMessage));

        final var actualOutput = Assertions.assertThrows(IllegalStateException.class, () -> useCase.execute(comand));

        Assertions.assertEquals(expectedIllegalExeceptionErrorMessage, actualOutput.getMessage());

        // then
        verify(categoryGateway, times(1)).create(argThat(category ->
                Objects.equals(expectedName, category.getName())
                        && Objects.equals(expectedDescription, category.getDescription())
                        && Objects.equals(expectedIsActive, category.isActive())
                        && Objects.nonNull(category.getId())
                        && Objects.nonNull(category.getUpdatedAt())
                        && Objects.nonNull(category.getCreatedAt())
                        && Objects.isNull(category.getDeletedAt())
        ));
    }
}
