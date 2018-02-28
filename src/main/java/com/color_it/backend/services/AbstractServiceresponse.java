package com.color_it.backend.services;

import com.color_it.backend.entities.AbstractEntity;

import java.util.List;

public class AbstractServiceResponse<T> {
    AbstractEntity entity;
    List<AbstractEntity> entities;

}
