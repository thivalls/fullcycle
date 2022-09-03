package com.fullcycle.admin.catalog.domain;

import com.fullcycle.admin.catalog.domain.category.CategoryID;

public abstract class AggregateRoot<ID extends Identifier> extends Entity<ID> {
    protected AggregateRoot(ID id) {
        super(id);
    }
}
