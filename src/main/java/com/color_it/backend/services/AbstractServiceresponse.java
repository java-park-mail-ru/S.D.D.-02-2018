package com.color_it.backend.services;

import com.color_it.backend.entities.AbstractEntity;

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

    public List<AbstractEntity> getEntities() {
        return entities;
    }

    public void setEntities(List<AbstractEntity> entities) {
        this.entities = entities;
    }
}
