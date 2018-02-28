package com.colorit.backend.services;

import com.colorit.backend.entities.AbstractEntity;

import java.util.List;

public class AbstractServiceResponse<T> {
    private AbstractEntity entity;
    @SuppressWarnings("unused")
    private List<AbstractEntity> entities;

    public AbstractEntity getEntity() {
        return entity;
    }

    public void setEntity(AbstractEntity entity) {
        this.entity = entity;
    }

    @SuppressWarnings("unused")
    public List<AbstractEntity> getEntities() {
        return entities;
    }

    @SuppressWarnings("unused")
    public void setEntities(List<AbstractEntity> entities) {
        this.entities = entities;
    }
}
