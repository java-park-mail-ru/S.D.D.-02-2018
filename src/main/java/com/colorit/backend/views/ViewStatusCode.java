package com.colorit.backend.views;

import org.springframework.http.HttpStatus;

public enum ViewStatusCode {
    OK_STATE(0, "ok", null, HttpStatus.OK),
    EMPTY_EMAIL(1, "email", "email_empty", HttpStatus.BAD_REQUEST),
    EMPTY_PASSWORD(2, "password", "password_empty", HttpStatus.BAD_REQUEST),
    EMPTY_PASSWORD_CHECK(3, "passwordCheck", "passwordCheck_empty", HttpStatus.BAD_REQUEST),
    EMPTY_OLD_PASSWORD(4, "oldPassword", "old_password_empty", HttpStatus.BAD_REQUEST),
    EMPTY_NICKNAME(5, "nickname", "nickname_empty", HttpStatus.BAD_REQUEST),
    INVALID_EMAIL_STATE(6, "email", "email_invalid", HttpStatus.BAD_REQUEST),
    PASSWORD_NOT_MATCH_STATE(7, "passwordCheck", "passwordCheck_not_match", HttpStatus.BAD_REQUEST),
    PHOTO_PATH_ERROR_STATE(8, "photo", "photo_empty", HttpStatus.BAD_REQUEST),
    EMPTY_UPDATE_FORM_STATE(9, "general", "empty_form", HttpStatus.BAD_REQUEST);

    private final Integer id;
    private final String field;
    private final String message;
    private final HttpStatus httpStatus;

    ViewStatusCode(Integer id, String desc, String message, HttpStatus httpStatus) {
        this.id = id;
        this.field = desc;
        this.message = message;
        this.httpStatus = HttpStatus.BAD_REQUEST;
    }

    public String getField() {
        return field;
    }

    public String getMessage() {
        return message;
    }

    @SuppressWarnings("unused")
    public HttpStatus getHttpStatus() {
        return httpStatus;
    }
}
