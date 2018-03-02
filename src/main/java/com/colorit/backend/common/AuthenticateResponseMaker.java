package com.colorit.backend.common;

import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.views.ResponseView;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AuthenticateResponseMaker extends AbstractResponseMaker {

    @Override
    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse,
                                                     MessageSource messageSource, Locale locale) {
        final String message = messageSource.getMessage(userServiceResponse.getStatus().getMessage(), null, locale);
        return makeResponse(userServiceResponse, message);
    }

}
