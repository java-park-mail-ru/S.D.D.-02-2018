package com.colorit.backend.entities;

import com.colorit.backend.views.output.ScalarView;

public class ScalarEntity<T> implements IEntity {
    T data;

    public ScalarEntity(T data) {
        this.data = data;
    }

    @Override
    public ScalarView<T> toView() {
        return new ScalarView<>(data);
    }
}
