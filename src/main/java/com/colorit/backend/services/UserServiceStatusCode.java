package com.colorit.backend.services;

public enum UserServiceStatusCode {
    OK_STATE,
    CREATED_STATE,
    CONFLICT_EMAIL_STATE,
    CONFLICT_NAME_STATE,
    PASSWORD_MATCH_ERROR_STATE,
    NAME_MATCH_ERROR_STATE,
    DB_ERROR_STATE
}
