package com.colorit.backend.views.output;

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
    private final HashMap<String, T> data = new HashMap<>();

    public ResponseView() {
    }

    public ResponseView(String field, T data) {
        this.data.put(field, data);
    }

    public void setData(String field, T data) {
        this.data.put(field, data);
    }

    public Map<String, T> getData() {
        return this.data;
    }

    public void addError(String field, String message) {
        errors.put(field, message);
    }

    // Methdos used for json serializer
    @SuppressWarnings("unused")
    public Map<String, String> getErrors() {
        return errors;
    }
}
