package com.fullcycle.admin.catalog.domain;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.exceptions.DomainException;
import com.fullcycle.admin.catalog.domain.validation.handlers.ThrowsValidationHandler;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

class CategoryTest {

//    @Test
//    @DisplayName("Should be possible create an instance of Category")
//    void test1 () {
//        Assertions.thr
//    }

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
}