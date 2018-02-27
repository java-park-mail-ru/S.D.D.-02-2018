package com.color_it.backend.services;

import org.springframework.http.ResponseEntity;

public interface AbstractServiceresponse<T> {
    ResponseEntity<T> toHttpResponse();
}
