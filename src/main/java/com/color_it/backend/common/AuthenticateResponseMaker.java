package com.color_it.backend.common;

import com.color_it.backend.services.UserServiceStatusCode;
import org.springframework.http.HttpStatus;

public class AuthenticateResponseMaker extends AbstractResponseMaker {
    public AuthenticateResponseMaker() {
        super();
        hashServiceStatusAndHttpStatus.put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.FORBIDDEN);
        hashServiceStatusAndMessage.put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, "forbidden_auth");
        hashServiceStatusAndMessage.put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, "forbidden_auth");
    }
}
