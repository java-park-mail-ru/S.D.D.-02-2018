package com.colorit.backend.common;

import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.services.UserServiceResponse;
import com.colorit.backend.views.ResponseView;
import com.colorit.backend.views.UserView;
import org.springframework.context.MessageSource;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.Locale;

@Component
public class AuthenticateResponseMaker extends AbstractResponseMaker {
    public AuthenticateResponseMaker() {
        super();
    }

    @Override
    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, MessageSource messageSource, Locale locale) {
        final ResponseView<UserView> responseView = new ResponseView<>();
        if (userServiceResponse.isValid()) {
            if (userServiceResponse.getEntity() != null) {
                responseView.setData(new UserView((UserEntity) userServiceResponse.getEntity()));
            }
        } else {
            String field = "general";
            if (userServiceResponse.getStatus().getField() != null) {
                field = userServiceResponse.getStatus().getField();
            }
            responseView.addError(field, messageSource.getMessage(userServiceResponse.getStatus().getMessage(),
                    null, locale));
        }
        return new ResponseEntity<>(responseView, userServiceResponse.getStatus().getHttpStatus());
    }


}
