package com.colorit.backend.services;

import com.colorit.backend.entities.AbstractEntity;

import java.util.List;

public class AbstractServiceResponse<T extends AbstractEntity> {
    private T entity;
    @SuppressWarnings("unused")
    private List<T> entities;

    public T getEntity() {
        return entity;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    @SuppressWarnings("unused")
    public List<T> getEntities() {
        return entities;
    }

    @SuppressWarnings("unused")
    public void setEntities(List<T> entities) {
        this.entities = entities;
    }
}
