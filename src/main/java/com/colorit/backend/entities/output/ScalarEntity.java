package com.colorit.backend.entities.output;

import com.colorit.backend.entities.IEntity;
import com.colorit.backend.views.output.ScalarView;

public class ScalarEntity<T> implements IEntity {
    private T data;

    public ScalarEntity(T data) {
        this.data = data;
    }

    @Override
    public ScalarView<T> toView() {
        return new ScalarView<>(data);
    }
}
