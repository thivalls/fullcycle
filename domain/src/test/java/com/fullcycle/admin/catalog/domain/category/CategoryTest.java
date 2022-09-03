package com.fullcycle.admin.catalog.domain.category;

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

}