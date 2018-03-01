package com.colorit.backend.common;

import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.services.UserServiceResponse;
import com.colorit.backend.services.UserServiceStatus;
import com.colorit.backend.services.UserServiceStatusCode;
import com.colorit.backend.views.ResponseView;
import com.colorit.backend.views.UserView;
import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Locale;
import java.util.Map;

/**
 * An basic class that represents responsemaker.
 *
 * @author HaseProgram - Dmitry Zaitsev
 * @version 1.0
 */
@Component
public class AbstractResponseMaker {
    UserServiceStatus userServiceStatus;
    private final Map<UserServiceStatusCode, String> hashServiceStatusAndMessage;

    public AbstractResponseMaker() {
        this.hashServiceStatusAndMessage = new EnumMap<UserServiceStatusCode, String>(UserServiceStatusCode.class) {
            {
                put(UserServiceStatusCode.OK_STATE, "ok");
                put(UserServiceStatusCode.CREATED_STATE, "created");
                put(UserServiceStatusCode.CONFLICT_EMAIL_STATE, "email_conflict");
                put(UserServiceStatusCode.CONFLICT_NAME_STATE, "nickname_conflict");
                put(UserServiceStatusCode.DB_ERROR_STATE, "server_error");
            }
        };
    }

    protected Map<UserServiceStatusCode, String> getHashServiceStatusAndMessage() {
        return hashServiceStatusAndMessage;
    }

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
            responseView.addError(field, messageSource.getMessage(
                    hashServiceStatusAndMessage.get(userServiceResponse.getStatus()), null, locale));
        }
        return new ResponseEntity<>(responseView, userServiceResponse.getStatus().getHttpStatus());
    }


    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, MessageSource messageSource, Locale locale) {
        final ResponseView responseView = new ResponseView();
        for (ViewStatusCode code : viewStatus.getErrors()) {
            responseView.addError(code.getField(),
                    messageSource.getMessage(code.getMessage(), null, locale));
        }
        return new ResponseEntity<>(responseView, HttpStatus.BAD_REQUEST);
    }
}