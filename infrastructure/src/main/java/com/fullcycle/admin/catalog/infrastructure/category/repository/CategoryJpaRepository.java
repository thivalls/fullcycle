package com.fullcycle.admin.catalog.infrastructure.category.repository;

import com.fullcycle.admin.catalog.infrastructure.category.persistence.CategoryJpaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CategoryJpaRepository extends JpaRepository<CategoryJpaEntity, String> {
}
