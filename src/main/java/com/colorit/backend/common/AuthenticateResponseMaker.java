package com.colorit.backend.common;

import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.views.output.ResponseView;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@Component
public class AuthenticateResponseMaker extends AbstractResponseMaker {

    public AuthenticateResponseMaker(@NotNull MessageSource messageSource) {
        super(messageSource);
    }

    @Override
    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, Locale locale,
                                                     String responseFieldName) {
        final String message = getMessageSource().getMessage(
                userServiceResponse.getStatus().getMessage(), null, locale);
        return makeResponse(userServiceResponse, message, responseFieldName);
    }
}

