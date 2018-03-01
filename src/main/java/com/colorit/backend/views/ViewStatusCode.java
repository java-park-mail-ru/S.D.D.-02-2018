package com.colorit.backend.views;

@SuppressWarnings("ALL")
public enum ViewStatusCode {
    OK_STATE(0, "ok"),
    EMPTY_EMAIL(1, "email"),
    EMPTY_PASSWORD(2, "password"),
    EMPTY_PASSWORD_CHECK(3, "passwordCheck"),
    EMPTY_OLD_PASSWORD(4, "oldPassword"),
    EMPTY_NICKNAME(5, "nickname"),
    INVALID_EMAIL_STATE(6, "email"),
    PASSWORD_NOT_MATCH_STATE(7, "passwordCheck"),
    PHOTO_PATH_ERROR_STATE(8, "photo");

    private Integer id;
    private String desc;

    ViewStatusCode(Integer id, String desc) {
        this.id = id;
        this.desc = desc;
    }

    public String getDesc() {
        return this.desc;
    }
}
