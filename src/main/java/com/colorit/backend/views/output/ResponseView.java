package com.colorit.backend.views.output;

import java.util.HashMap;
import java.util.Map;

/**
 * Represents output view.
 *
 * @author hustonMavr - Barulev Alexandr
 * @version 1.0
 */
public class ResponseView {
    private final Map<String, String> errors = new HashMap<>();
    private IOutputView data;

    public ResponseView() {
    }

    public ResponseView(IOutputView data) {
        this.data = data;
    }

    public void setData(IOutputView data) {
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
    public IOutputView getData() {
        return data;
    }
}
