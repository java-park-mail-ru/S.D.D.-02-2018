package  com.color_it.backend.common;

import org.springframework.http.ResponseEntity;

public interface AbstractServiceresponse<T> {
    ResponseEntity<T> toHttpResponse();
}
