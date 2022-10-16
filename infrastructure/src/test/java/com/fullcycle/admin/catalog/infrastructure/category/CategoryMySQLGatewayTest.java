package com.fullcycle.admin.catalog.infrastructure.category;

import com.fullcycle.admin.catalog.domain.category.Category;
import com.fullcycle.admin.catalog.domain.category.CategoryID;
import com.fullcycle.admin.catalog.domain.category.CategorySearchQuery;
import com.fullcycle.admin.catalog.infrastructure.MySQLGatewayTest;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

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

    @Test
    @DisplayName("Should update a category with correct params")
    void test2() {
        final var expectedName = "Film";
        final var expectedDescription = "A categoria";
        final var expectedIsActive = false;

        final var expectedUpdatedName = "Filmes";
        final var expectedUpdatedDescription = "As categorias mais assistidas";
        final var expectedUpdatedIsActive = true;

        final var baseCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertNotNull(baseCategory.getId());
        Assertions.assertNotNull(baseCategory.getCreatedAt());
        Assertions.assertNotNull(baseCategory.getUpdatedAt());
        Assertions.assertNotNull(baseCategory.getDeletedAt());
        Assertions.assertEquals(expectedName, baseCategory.getName());
        Assertions.assertEquals(expectedDescription, baseCategory.getDescription());
        Assertions.assertEquals(expectedIsActive, baseCategory.isActive());

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAndFlush(CategoryJpaEntity.from(baseCategory)).toAggregate();

        Assertions.assertEquals(1, categoryJpaRepository.count());

        final var clonedCategory =
                baseCategory.clone().update(expectedUpdatedName, expectedUpdatedDescription, expectedUpdatedIsActive);

        final var updatedCategory = categoryGateway.update(clonedCategory);

        Assertions.assertEquals(1, categoryJpaRepository.count());

        Assertions.assertEquals(baseCategory.getId(), updatedCategory.getId());
        Assertions.assertEquals(expectedUpdatedName, updatedCategory.getName());
        Assertions.assertEquals(expectedUpdatedDescription, updatedCategory.getDescription());
        Assertions.assertEquals(expectedUpdatedIsActive, updatedCategory.isActive());
        Assertions.assertEquals(baseCategory.getCreatedAt(), updatedCategory.getCreatedAt());
        Assertions.assertTrue(baseCategory.getUpdatedAt().isBefore(updatedCategory.getUpdatedAt()));
        Assertions.assertNull(updatedCategory.getDeletedAt());

        CategoryJpaEntity foundCategory = categoryJpaRepository.findById(updatedCategory.getId().getValue()).get();

        Assertions.assertEquals(updatedCategory.getId().getValue(), foundCategory.getId());
        Assertions.assertEquals(expectedUpdatedName, foundCategory.getName());
        Assertions.assertEquals(expectedUpdatedDescription, foundCategory.getDescription());
        Assertions.assertEquals(expectedUpdatedIsActive, foundCategory.isActive());
        Assertions.assertEquals(updatedCategory.getCreatedAt(), foundCategory.getCreatedAt());
        Assertions.assertTrue(baseCategory.getUpdatedAt().isBefore(foundCategory.getUpdatedAt()));
        Assertions.assertEquals(updatedCategory.getDeletedAt(), foundCategory.getDeletedAt());
        Assertions.assertNull(foundCategory.getDeletedAt());
    }

    @Test
    @DisplayName("Should delete a category from database")
    void test3() {
        final var expectedName = "Film";
        final var expectedDescription = "A categoria";
        final var expectedIsActive = false;

        final var baseCategory = Category.newCategory(expectedName, expectedDescription, expectedIsActive);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAndFlush(CategoryJpaEntity.from(baseCategory)).toAggregate();

        Assertions.assertEquals(1, categoryJpaRepository.count());

        categoryGateway.deleteById(baseCategory.getId());

        Assertions.assertEquals(0, categoryJpaRepository.count());
    }

    @Test
    @DisplayName("Should not throw error when it tries to delete a category from database with invalid id")
    void test4() {

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryGateway.deleteById(CategoryID.from("invalid"));

        Assertions.assertEquals(0, categoryJpaRepository.count());
    }

    @Test
    @DisplayName("Should find a category with valid id")
    void test5() {
        final var expectedName1 = "primeira";
        final var expectedDescription1 = "As categorias mais assistidas";
        final var expectedIsActive1 = true;

        final var expectedName2 = "segunda";
        final var expectedDescription2 = "As categorias mais assistidas";
        final var expectedIsActive2 = true;

        final var baseCategory1 = Category.newCategory(expectedName1, expectedDescription1, expectedIsActive1);
        final var baseCategory2 = Category.newCategory(expectedName2, expectedDescription2, expectedIsActive2);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAndFlush(CategoryJpaEntity.from(baseCategory1)).toAggregate();
        categoryJpaRepository.saveAndFlush(CategoryJpaEntity.from(baseCategory2)).toAggregate();

        Assertions.assertEquals(2, categoryJpaRepository.count());

        final var foundCategory = categoryGateway.findById(baseCategory2.getId()).get();

        Assertions.assertEquals(baseCategory2.getId(), foundCategory.getId());
        Assertions.assertEquals(expectedName2, foundCategory.getName());
        Assertions.assertEquals(expectedDescription2, foundCategory.getDescription());
        Assertions.assertEquals(expectedIsActive2, foundCategory.isActive());
        Assertions.assertEquals(baseCategory2.getCreatedAt(), foundCategory.getCreatedAt());
        Assertions.assertEquals(baseCategory2.getUpdatedAt(), foundCategory.getUpdatedAt());
        Assertions.assertNull(foundCategory.getDeletedAt());
    }

    @Test
    @DisplayName("Should not throw error when it tries finding a category with invalid id")
    void test6() {
        Assertions.assertEquals(0, categoryJpaRepository.count());
        final var foundCategory = categoryGateway.findById(CategoryID.from("invalido"));
        Assertions.assertTrue(foundCategory.isEmpty());
    }

    @Test
    @DisplayName("Should return first page and first item with asc sort with correct query params")
    void test7() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Séries", null, true);
        final var documentarios = Category.newCategory("Documentários", null, true);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryJpaRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
    }

    @Test
    @DisplayName("testes")
    void test8() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 0;

        Assertions.assertEquals(0, categoryJpaRepository.count());

        final var query = new CategorySearchQuery(0, 1, "", "name", "asc");
        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(0, actualResult.items().size());
    }

    @Test
    @DisplayName("testes")
    void test9() {
        var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 3;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Séries", null, true);
        final var documentarios = Category.newCategory("Documentários", null, true);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryJpaRepository.count());

        var query = new CategorySearchQuery(0, 1, "", "name", "asc");
        var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());

        // Page 1
        expectedPage = 1;

        query = new CategorySearchQuery(1, 1, "", "name", "asc");
        actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(filmes.getId(), actualResult.items().get(0).getId());

        // Page 2
        expectedPage = 2;

        query = new CategorySearchQuery(2, 1, "", "name", "asc");
        actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(series.getId(), actualResult.items().get(0).getId());
    }

    @Test
    @DisplayName("tesets")
    void test10() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var filmes = Category.newCategory("Filmes", null, true);
        final var series = Category.newCategory("Séries", null, true);
        final var documentarios = Category.newCategory("Documentários", null, true);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryJpaRepository.count());

        final var query = new CategorySearchQuery(0, 1, "doc", "name", "asc");
        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(documentarios.getId(), actualResult.items().get(0).getId());
    }
    @Test
    @DisplayName("teste")
    void test11() {
        final var expectedPage = 0;
        final var expectedPerPage = 1;
        final var expectedTotal = 1;

        final var filmes = Category.newCategory("Filmes", "A categoria mais assistida", true);
        final var series = Category.newCategory("Séries", "Uma categoria assistida", true);
        final var documentarios = Category.newCategory("Documentários", "A categoria menos assistida", true);

        Assertions.assertEquals(0, categoryJpaRepository.count());

        categoryJpaRepository.saveAll(List.of(
                CategoryJpaEntity.from(filmes),
                CategoryJpaEntity.from(series),
                CategoryJpaEntity.from(documentarios)
        ));

        Assertions.assertEquals(3, categoryJpaRepository.count());

        final var query = new CategorySearchQuery(0, 1, "MAIS ASSISTIDA", "name", "asc");
        final var actualResult = categoryGateway.findAll(query);

        Assertions.assertEquals(expectedPage, actualResult.currentPage());
        Assertions.assertEquals(expectedPerPage, actualResult.perPage());
        Assertions.assertEquals(expectedTotal, actualResult.total());
        Assertions.assertEquals(expectedPerPage, actualResult.items().size());
        Assertions.assertEquals(filmes.getId(), actualResult.items().get(0).getId());
    }
}