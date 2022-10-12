package com.fullcycle.admin.catalog.infrastructure.category;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.infrastructure.MySQLGatewayTest;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@MySQLGatewayTest
class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;


    @Test
    @DisplayName("Should be possible to create a category persinting in databsase table categories")
    void test() {
        final var expectedName = "Filmes";
        final var expectedDescription = "A categoria mais assistida";
        final var expectedIsActive = true;

        final var baseCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        Category createdCategory = categoryGateway.create(baseCategory);

        Assertions.assertEquals(1, categoryJpaRepository.count());

        Assertions.assertEquals(baseCategory.getId(), createdCategory.getId());
        Assertions.assertEquals(expectedName, createdCategory.getName());
        Assertions.assertEquals(expectedDescription, createdCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, createdCategory.isActive());
        Assertions.assertEquals(baseCategory.getCreatedAt(), createdCategory.getCreatedAt());
        Assertions.assertEquals(baseCategory.getUpdatedAt(), createdCategory.getUpdatedAt());
        Assertions.assertEquals(baseCategory.getDeletedAt(), createdCategory.getDeletedAt());
        Assertions.assertNull(createdCategory.getDeletedAt());

        CategoryJpaEntity actualEntity = categoryJpaRepository.findById(createdCategory.getId().getValue()).get();

        Assertions.assertEquals(baseCategory.getId().getValue(), actualEntity.getId());
        Assertions.assertEquals(expectedName, actualEntity.getName());
        Assertions.assertEquals(expectedDescription, actualEntity.getDescription());
        Assertions.assertEquals(expectedIsActive, actualEntity.isActive());
        Assertions.assertEquals(baseCategory.getCreatedAt(), actualEntity.getCreatedAt());
        Assertions.assertEquals(baseCategory.getUpdatedAt(), actualEntity.getUpdatedAt());
        Assertions.assertEquals(baseCategory.getDeletedAt(), actualEntity.getDeletedAt());
        Assertions.assertNull(actualEntity.getDeletedAt());

    }
}