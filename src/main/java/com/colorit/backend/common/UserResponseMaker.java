package com.colorit.backend.common;

import com.colorit.backend.services.UserServiceStatusCode;

public class UserResponseMaker extends AbstractResponseMaker {
    public UserResponseMaker() {
        super();
        hashServiceStatusAndMessage.put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, "forbidden_session");
        hashServiceStatusAndMessage.put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, "forbidden_password");
    }
}