package com.fullcycle.admin.catalog.infrastructure.category;

import com.fullcycle.admin.catalog.infrastructure.MySQLGatewayTest;
import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaRepository;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

@MySQLGatewayTest
class CategoryMySQLGatewayTest {

    @Autowired
    private CategoryMySQLGateway categoryGateway;

    @Autowired
    private CategoryJpaRepository categoryJpaRepository;


    @Test
    void test() {
        Assertions.assertNotNull(categoryGateway);
        Assertions.assertNotNull(categoryJpaRepository);
    }
}