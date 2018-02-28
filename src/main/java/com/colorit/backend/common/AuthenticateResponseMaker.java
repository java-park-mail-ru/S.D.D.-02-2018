package com.colorit.backend.common;

import com.colorit.backend.services.UserServiceStatusCode;

public class AuthenticateResponseMaker extends AbstractResponseMaker {
    public AuthenticateResponseMaker() {
        super();
        super().getHashServiceStatusAndMessage.put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, "forbidden_auth");
        super().getHashServiceStatusAndMessage..put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, "forbidden_auth");
    }
}
