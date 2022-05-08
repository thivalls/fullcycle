package com.fullcycle.admin.catalog.domain;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.validation.handlers.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

    @Test
    @DisplayName("Should be possible to create a new category with correct params")
    void test1 () {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        Category category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(category);
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.getActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNull(category.getDeletedAt());
    }

    @Test
    @DisplayName("Should throw error when name is null")
    void test2 () {
        final String expectedName = null;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must not be null";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var categoryException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorCount, categoryException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, categoryException.getErrors().get(0).message());
    }

    @Test
    @DisplayName("Should throw error when name is empty")
    void test3 () {
        final var expectedName = "";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' must not be empty";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var categoryException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorCount, categoryException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, categoryException.getErrors().get(0).message());
    }

    @Test
    @DisplayName("Should throw error when name is less than 3")
    void test4 () {
        final var expectedName = "fi ";
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' size must be between 3 and 255";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var categoryException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorCount, categoryException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, categoryException.getErrors().get(0).message());
    }

    @Test
    @DisplayName("Should throw error when name is greater than 255")
    void test5 () {
        final var expectedName = """
                O Fabuloso Gerador de Lero-lero v2.0 é capaz de gerar qualquer quantidade de texto vazio e prolixo, ideal para engrossar uma tese de mestrado, impressionar seu chefe ou preparar discursos capazes de curar a insônia da platéia. Basta informar um título pomposo qualquer (nos moldes do que está sugerido aí embaixo) e a quantidade de frases desejada. Voilá! Em dois nano-segundos você terá um texto - ou mesmo um livro inteiro - pronto para impressão. Ou, se preferir, faça copy/paste para um editor de texto para formatá-lo mais sofisticadamente. Lembre-se: aparência é tudo, conteúdo é nada.
                """;
        final var expectedErrorCount = 1;
        final var expectedErrorMessage = "'name' size must be between 3 and 255";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var categoryException = Assertions.assertThrows(DomainException.class, () -> category.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorCount, categoryException.getErrors().size());
        Assertions.assertEquals(expectedErrorMessage, categoryException.getErrors().get(0).message());
    }

    @Test
    @DisplayName("Should be possible to create a new category without description")
    void test6 () {
        final var expectedName = "Filmes";
        final var expectedDescription = "";
        final var expectedIsActive = true;

        Assertions.assertDoesNotThrow(() -> Category.newCategory(expectedName, expectedDescription, expectedIsActive));
    }

    @Test
    @DisplayName("Should be possible to create a new category with isActive being false")
    void test7 () {
        final var expectedName = "Filmes";
        final var expectedDescription = "";
        final var expectedIsActive = false;

        final var category = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertDoesNotThrow(() -> Category.newCategory(expectedName, expectedDescription, expectedIsActive));
        Assertions.assertNotNull(category);
        Assertions.assertEquals(expectedName, category.getName());
        Assertions.assertEquals(expectedDescription, category.getDescription());
        Assertions.assertEquals(expectedIsActive, category.getActive());
        Assertions.assertNotNull(category.getCreatedAt());
        Assertions.assertNotNull(category.getUpdatedAt());
        Assertions.assertNotNull(category.getDeletedAt());
    }

    @Test
    @DisplayName("Should be possible to deactivate a category")
    void test8 () {
        final var expectedName = "Filmes";
        final var expectedDescription = "Some description";
        final var expectedIsActive = false;

        Assertions.assertDoesNotThrow(() -> Category.newCategory(expectedName, expectedDescription, true));

        final var category = Category.newCategory(expectedName, expectedDescription, true);

        final var updatedAt = category.getUpdatedAt();

        final Category updatedCategory = category.deactivate();

        Assertions.assertEquals(category.getId(), updatedCategory.getId());
        Assertions.assertNotNull(updatedCategory);
        Assertions.assertEquals(expectedName, updatedCategory.getName());
        Assertions.assertEquals(expectedDescription, updatedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, updatedCategory.getActive());
        Assertions.assertNotNull(updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(updatedCategory.getDeletedAt());
    }

    @Test
    @DisplayName("Should be possible to activate a category")
    void test9 () {
        final var expectedName = "Filmes";
        final var expectedDescription = "Some description";
        final var expectedIsActive = true;

        Assertions.assertDoesNotThrow(() -> Category.newCategory(expectedName, expectedDescription, true));

        final var category = Category.newCategory(expectedName, expectedDescription, false);

        Assertions.assertFalse(category.getActive());
        Assertions.assertNotNull(category.getDeletedAt());

        final var updatedAt = category.getUpdatedAt();

        final Category updatedCategory = category.activate();

        Assertions.assertEquals(category.getId(), updatedCategory.getId());
        Assertions.assertNotNull(updatedCategory);
        Assertions.assertEquals(expectedName, updatedCategory.getName());
        Assertions.assertEquals(expectedDescription, updatedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, updatedCategory.getActive());
        Assertions.assertNotNull(updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(updatedCategory.getDeletedAt());
    }

    @Test
    @DisplayName("Should be possible to update a category")
    void test10 () {
        final var expectedName = "updated name";
        final var expectedDescription = "updated description";
        final var expectedIsActive = true;
        final var category = Category.newCategory("Filme", "Some description", false);
        Assertions.assertNotNull(category);

        final var updatedAt = category.getUpdatedAt();
        final var createdAt = category.getCreatedAt();

        final Category updatedCategory = category.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(category.getId(), updatedCategory.getId());
        Assertions.assertNotNull(updatedCategory);
        Assertions.assertEquals(expectedName, updatedCategory.getName());
        Assertions.assertEquals(expectedDescription, updatedCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, updatedCategory.getActive());
        Assertions.assertEquals(createdAt, updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNull(updatedCategory.getDeletedAt());
    }

    @Test
    @DisplayName("Should be possible to update a category with 'isActive' false")
    void test11 () {
        final var expectedName = "updated name";
        final var expectedDescription = "updated description";
        final var expectedIsActive = false;
        final var category = Category.newCategory("Filme", "Some description", true);
        Assertions.assertNotNull(category);

        final var updatedAt = category.getUpdatedAt();
        final var createdAt = category.getCreatedAt();

        final Category updatedCategory = category.update(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(category.getId(), updatedCategory.getId());
        Assertions.assertEquals(expectedName, updatedCategory.getName());
        Assertions.assertEquals(expectedDescription, updatedCategory.getDescription());
        Assertions.assertNotNull(updatedCategory);
        Assertions.assertEquals(expectedIsActive, updatedCategory.getActive());
        Assertions.assertEquals(createdAt, updatedCategory.getCreatedAt());
        Assertions.assertTrue(updatedCategory.getUpdatedAt().isAfter(updatedAt));
        Assertions.assertNotNull(updatedCategory.getDeletedAt());
    }
}