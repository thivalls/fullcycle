package com.fullcycle.admin.catalog.domain;

import com.fullcycle.admin.catalog.domain.category.Category;
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
    void test2 () {
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
}