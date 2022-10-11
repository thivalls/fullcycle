package com.fullcycle.admin.catalog.domain.category;

import com.fullcycle.admin.catalog.domain.AggregateRoot;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> implements Cloneable {
    private String name;
    private String description;
    private boolean active;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;

    private Category(
            final CategoryID id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        super(id);
        this.name = name;
        this.description = description;
        this.active = active;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(String name, String description, boolean active) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var isActiveCheck = active ? null : now;
        return new Category(id, name, description, active, now, now, isActiveCheck);
    }
    public static Category with(
            final CategoryID id,
            final String name,
            final String description,
            final boolean active,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt
    ) {
        return new Category(
                id,
                name,
                description,
                active,
                createdAt,
                updatedAt,
                deletedAt
        );
    }
    public static Category with(final Category category) {
        return with(
                category.getId(),
                category.name,
                category.description,
                category.isActive(),
                category.createdAt,
                category.updatedAt,
                category.deletedAt
        );
    }

    @Override
    public void validate(final ValidationHandler validationHandler) {
        new CategoryValidator(this, validationHandler).validate();
    }

    public CategoryID getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public boolean isActive() {
        return active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public Category deactivate() {
        if (!this.isActive()) {
            return this;
        }
        this.active = false;
        final var now = Instant.now();
        this.deletedAt = now;
        this.updatedAt = now;
        return this;
    }

    public Category activate() {
        if (this.isActive()) {
            return this;
        }
        this.active = true;
        this.deletedAt = null;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category update(
            final String name,
            final String description,
            final boolean active
    ) {
        if (active) {
            activate();
        } else {
            deactivate();
        }
        this.name = name;
        this.description = description;
        this.updatedAt = Instant.now();
        return this;
    }

    @Override
    public Category clone() {
        try {
            Category clone = (Category) super.clone();
            return clone;
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
