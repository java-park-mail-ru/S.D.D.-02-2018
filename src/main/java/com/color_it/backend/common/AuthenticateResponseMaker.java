package com.color_it.backend.common;

import com.color_it.backend.services.UserServiceResponse;
import com.color_it.backend.services.UserServiceStatusCode;
import com.color_it.backend.views.ResponseView;
import com.color_it.backend.views.ViewStatus;
import org.apache.catalina.User;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Locale;
import java.util.Map;

public class AuthenticateResponseMaker extends AbstractResponseMaker {
    public AuthenticateResponseMaker() {
        super(Map.ofEntries(
//                Map.entry()

        ));
    }

//    private static final Map<UserServiceStatusCode, HttpStatus> hashServiceCodeAndHttpCode = Map.ofEntries(
//
//    );


    @Override
    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, MessageSource messageSource, Locale locale) {
        return super.makeResponse(viewStatus, messageSource, locale);
    }

    @Override
    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, MessageSource messageSource, Locale locale) {
        return null;
    }
}
