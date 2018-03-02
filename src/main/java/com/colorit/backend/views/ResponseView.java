package com.colorit.backend.views;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents output view.
 *
 * @author hustonMavr - Barulev Alexandr
 * @version 1.0
 */
public class ResponseView<T> {
    private final Map<String, String> errors = new HashMap<>();
    private T data;

    public ResponseView() {}

    public ResponseView(T data) {
        this.data = data;
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
