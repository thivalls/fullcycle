package com.fullcycle.admin.catalog.infrastructure.category;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.infrastructure.MySQLGatewayTest;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaRepository;
import org.hibernate.PropertyValueException;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;

@MySQLGatewayTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;

    private String expectedName;
    private String expectedDescription;
    private boolean expectedIsActive;

    @BeforeEach
    void beforeEach() {
        this.expectedName = "Filmes";
        this.expectedDescription = "A categoria mais assistida";
        this.expectedIsActive = true;
    }

    @Test
    @DisplayName("Should return error with null name param")
    void test1() {
        final var expectedErrorMessage = "not-null property references a null or transient value : com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity.name";
        final var expectedErrorPropertyField = "name";

        final var baseCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var entity = CategoryJpaEntity.from(baseCategory);

        entity.setName(null);

        final var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryJpaRepository.save(entity));

        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(expectedErrorPropertyField, cause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, cause.getMessage());
    }

    @Test
    @DisplayName("Should return error with null createdAt param")
    void test2() {
        final var expectedErrorMessage = "not-null property references a null or transient value : com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity.createdAt";
        final var expectedErrorPropertyField = "createdAt";

        final var baseCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var entity = CategoryJpaEntity.from(baseCategory);

        entity.setCreatedAt(null);

        final var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryJpaRepository.save(entity));

        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(expectedErrorPropertyField, cause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, cause.getMessage());
    }

    @Test
    @DisplayName("Should return error with null updatedAt param")
    void test3() {
        final var expectedErrorMessage = "not-null property references a null or transient value : com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity.updatedAt";
        final var expectedErrorPropertyField = "updatedAt";

        final var baseCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        final var entity = CategoryJpaEntity.from(baseCategory);

        entity.setUpdatedAt(null);

        final var exception = Assertions.assertThrows(DataIntegrityViolationException.class, () -> categoryJpaRepository.save(entity));

        final var cause = Assertions.assertInstanceOf(PropertyValueException.class, exception.getCause());

        Assertions.assertEquals(expectedErrorPropertyField, cause.getPropertyName());
        Assertions.assertEquals(expectedErrorMessage, cause.getMessage());
    }
}
