package com.color_it.backend.views;

import java.util.HashMap;
import java.util.Map;

/**
 * Output view
 */
public class ResponseView<T> {
    private final Map<String, String> errors;
    private T data;

    public ResponseView() {
        errors = new HashMap<>();
    }

    public void setData(T data) {
        this.data = data;
    }

    public void addError(String field, String message) {
        errors.put(field, message);
    }

    // Methdos used for json serializer
    @SuppressWarnings("unused")
    public Map<String, String> getErrors() {
        return errors;
    }

    @SuppressWarnings("unused")
    public T getData() {
        return data;
    }
}
