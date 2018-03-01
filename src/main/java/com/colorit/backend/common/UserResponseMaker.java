package com.colorit.backend.common;

import com.colorit.backend.services.UserServiceStatusCode;
import org.springframework.stereotype.Component;

@Component
public class UserResponseMaker extends AbstractResponseMaker {
    public UserResponseMaker() {
        super();
        this.getHashServiceStatusAndMessage().put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, "forbidden_session");
        this.getHashServiceStatusAndMessage().put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, "forbidden_password");
    }
}