package com.color_it.backend.views;

import java.util.HashMap;
import java.util.Map;

/**
 * Output view
 */
public class ResponseView<T> {
    private final Map<String, String> errors;
    T data;
//    private String message;

    public ResponseView() {
        errors = new HashMap<>();
    }

    public ResponseView(String message) {
//        this.message = message;
        errors = new HashMap<>();
    }
//
//    public void setMessage(String message) {
//        this.message = message;
//    }

    public void setData(T data) {
        this.data = data;
    }

    public void addError(String field, String message) {
        errors.put(field, message);
    }

    // Methdos used for json serializer
    public Map<String, String> getErrors() {
        return errors;
    }

//    public String getMessage() {
//        return message;
//    }

    public T getData() {
        return data;
    }
}
