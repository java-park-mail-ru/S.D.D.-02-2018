package com.colorit.backend.services;

import org.springframework.http.HttpStatus;

public enum UserServiceStatus {
    OK_STATE(0, null, "ok",HttpStatus.OK),
    CREATED_STATE(1, null, "created", HttpStatus.CREATED),
    CONFLICT_EMAIL_STATE(2, "email", "email_conflict", HttpStatus.CONFLICT),
    CONFLICT_NAME_STATE(3, "nickname", "nickname_conflict", HttpStatus.CONFLICT),
    PASSWORD_MATCH_ERROR_STATE(4, null, "forbidden", HttpStatus.FORBIDDEN),
    NAME_MATCH_ERROR_STATE(5, null, "forbidden", HttpStatus.FORBIDDEN),
    DB_ERROR_STATE(6, null, "server_error", HttpStatus.INTERNAL_SERVER_ERROR);

    private Integer id;
    private String field;
    private String message;
    private HttpStatus httpStatus;

    UserServiceStatus(Integer id, String field, String message, HttpStatus httpStatus) {
        this.id = id;
        this.field = field;
        this.message = message;
        this.httpStatus = httpStatus;
    }

    public String getField() {
        return field;
    }
}
