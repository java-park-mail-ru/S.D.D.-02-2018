package com.color_it.backend.common;

import com.color_it.backend.services.UserServiceStatusCode;

public class UserResponseMaker extends AbstractResponseMaker {
    public UserResponseMaker() {
        super();
        //hashServiceStatusAndHttpStatus.put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND);
        hashServiceStatusAndMessage.put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, "forbidden_session");
        hashServiceStatusAndMessage.put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, "forbidden_password");
    }
}