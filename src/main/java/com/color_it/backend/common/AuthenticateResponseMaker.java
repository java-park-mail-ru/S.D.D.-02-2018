package com.color_it.backend.common;

import com.color_it.backend.services.UserServiceResponse;
import com.color_it.backend.services.UserServiceStatusCode;
import com.color_it.backend.views.ResponseView;
import com.color_it.backend.views.ViewStatus;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;
// TODO change messages on forbidden -> auth input new tr
//                                   -> user invalid auth data
// think about different messages
public class AuthenticateResponseMaker extends AbstractResponseMaker {
    public AuthenticateResponseMaker() {
        super();
        hashServiceStatusAndHttpStatus.put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.FORBIDDEN);
        hashServiceStatusAndMessage.put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, "forbidden_auth");
        hashServiceStatusAndMessage.put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, "forbidden_auth");
    }

    // maybe its not neccessary
    @Override
    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, MessageSource messageSource, Locale locale) {
        return super.makeResponse(viewStatus, messageSource, locale);
    }

    @Override
    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, MessageSource messageSource, Locale locale) {
        return super.makeResponse(userServiceResponse, messageSource, locale);
    }
}
