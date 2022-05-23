package com.fullcycle.admin.catalog.domain.category;

import com.fullcycle.admin.catalog.domain.AggregateRoot;
import com.fullcycle.admin.catalog.domain.validation.ValidationHandler;

import java.time.Instant;

public class Category extends AggregateRoot<CategoryID> implements Cloneable{
    private String name;
    private String description;
    private Boolean isActive;
    private Instant createdAt;
    private Instant updatedAt;
    private Instant deletedAt;


    private Category(
            final CategoryID id,
            final String name,
            final String description,
            final boolean isActive,
            final Instant createdAt,
            final Instant updatedAt,
            final Instant deletedAt) {
        super(id);
        this.name = name;
        this.description = description;
        this.isActive = isActive;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.deletedAt = deletedAt;
    }

    public static Category newCategory(final String name, final String description, final boolean isActive) {
        final var id = CategoryID.unique();
        final var now = Instant.now();
        final var deletedAt = isActive ? null : now;

        return new Category(id, name, description, isActive, now, now, deletedAt);
    }

    public Category deactivate() {
        if (getDeletedAt() == null) {
            this.deletedAt = Instant.now();
        }
        this.isActive = false;
        this.updatedAt = Instant.now();
        return this;
    }

    public Category activate() {
        this.isActive = true;
        this.updatedAt = Instant.now();
        this.deletedAt = null;
        return this;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getDeletedAt() {
        return deletedAt;
    }

    public void setDeletedAt(Instant deletedAt) {
        this.deletedAt = deletedAt;
    }

    @Override
    public void validate(final ValidationHandler validationHandler) {
        new CategoryValidator(this, validationHandler).validate();
    }

    public Category update(final String name, final String description, final boolean isActive) {
        if(isActive) {
            activate();
        } else {
            deactivate();
        }
        this.setName(name);
        this.setDescription(description);
        this.setUpdatedAt(Instant.now());
        return this;
    }

    @Override
    public Category clone() {
        try {
            return (Category) super.clone();
        } catch (CloneNotSupportedException e) {
            throw new AssertionError();
        }
    }
}
