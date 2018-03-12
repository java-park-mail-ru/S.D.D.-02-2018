package com.colorit.backend.views.entity;

public class ScalarView<T> {
    private T data;

    public ScalarView(T data) {
        this.data = data;
    }

    public T getData() {
        return data;
    }
}
