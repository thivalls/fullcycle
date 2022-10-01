package com.fullcycle.admin.catalog.domain.category;

import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.validation.handler.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class CategoryTest {

    @Test
    @DisplayName("Should create a category with all params")
    public void test1() {
        // given
        final String expectedName = "Filmes";
        final String expectedDescription = "Os filmes mais assistidos";
        final boolean expectedIsActive = true;

        // do
        Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        // then
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getName());
        Assertions.assertNotNull(actualCategory.getDescription());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    @DisplayName("Should not create a category with null name")
    public void test2() {
        // given
        final String expectedName = null;
        final var expectedErrorsCount = 1;
        final var expectedErrorMessage = "The field {name} must not be null";
        final String expectedDescription = "Os filmes mais assistidos";
        final boolean expectedIsActive = true;

        // do
        Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        // then
        final var actualCategoryException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualCategoryException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorsCount, actualCategoryException.getErrors().size());
    }

    @Test
    @DisplayName("Should not create a category with empty name")
    public void test3() {
        // given
        final String expectedName = "  ";
        final var expectedErrorsCount = 1;
        final var expectedErrorMessage = "The field {name} must not be empty";
        final String expectedDescription = "Os filmes mais assistidos";
        final boolean expectedIsActive = true;

        // do
        Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        // then
        final var actualCategoryException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualCategoryException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorsCount, actualCategoryException.getErrors().size());
    }

    @Test
    @DisplayName("Should not create a category with less than 3 characters")
    public void test4() {
        // given
        final String expectedName = "fi ";
        final var expectedErrorsCount = 1;
        final var expectedErrorMessage = "The field {name} must be between 3 and 255 characters";
        final String expectedDescription = "Os filmes mais assistidos";
        final boolean expectedIsActive = true;

        // do
        Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        // then
        final var actualCategoryException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualCategoryException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorsCount, actualCategoryException.getErrors().size());
    }

    @Test
    @DisplayName("Should not create a category with more than 255 characters")
    public void test5() {
        // given
        final String expectedName = """
            Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenase dignissim laoreet congue. Nunc ut molestie mi, sed pharetra magna. 
            Donec sollicitudin tempus ante. Etiam vel dui imperdiet, convallis tellus pretium, maximus massa. Quisque a arcu a libero.
            """;
        final var expectedErrorsCount = 1;
        final var expectedErrorMessage = "The field {name} must be between 3 and 255 characters";
        final String expectedDescription = "Os filmes mais assistidos";
        final boolean expectedIsActive = true;

        // do
        Category actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        // then
        final var actualCategoryException = Assertions.assertThrows(DomainException.class, () -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertEquals(expectedErrorMessage, actualCategoryException.getErrors().get(0).message());
        Assertions.assertEquals(expectedErrorsCount, actualCategoryException.getErrors().size());
    }

    @Test
    @DisplayName("Should create a category with empty description field")
    public void test6() {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "    ";
        final var expectedIsActive = true;

        // do
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        // then
        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getName());
        Assertions.assertNotNull(actualCategory.getDescription());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNull(actualCategory.getDeletedAt());
    }

    @Test
    @DisplayName("Should create a category with false isActive field")
    public void test7() {
        // given
        final var expectedName = "Filmes";
        final var expectedDescription = "    ";
        final var expectedIsActive = false;

        // do
        final var actualCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        // then
        Assertions.assertDoesNotThrow(() -> actualCategory.validate(new ThrowsValidationHandler()));
        Assertions.assertNotNull(actualCategory.getId());
        Assertions.assertNotNull(actualCategory.getName());
        Assertions.assertNotNull(actualCategory.getDescription());
        Assertions.assertNotNull(actualCategory.getCreatedAt());
        Assertions.assertNotNull(actualCategory.getUpdatedAt());
        Assertions.assertNotNull(actualCategory.getDeletedAt());
    }
}