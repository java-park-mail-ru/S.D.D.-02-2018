package com.colorit.backend.common;

import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.storages.responses.StorageResponse;
import com.colorit.backend.views.output.ResponseView;
import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Locale;

/**
 * An basic class that represents responsemaker.
 *
 * @author HaseProgram - Dmitry Zaitsev
 * @version 1.0
 */
@Primary
@Component
public abstract class AbstractResponseMaker {

    private final @NotNull MessageSource messageSource;

    public AbstractResponseMaker(@NotNull MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    public ResponseEntity<ResponseView> makeUnauthorizedResponse(Locale locale) {
        final ResponseView responseView = new ResponseView();
        responseView.addError("general", messageSource.getMessage("unauthorized", null, locale));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseView);
    }

    public ResponseEntity<ResponseView> authorizedResponse(UserServiceResponse userServiceResponse,
                                                           HttpSession httpSession, String field) {
        final ResponseView<String> responseView = new ResponseView<>();
        responseView.setData(field, httpSession.getId());
        return new ResponseEntity<>(responseView, userServiceResponse.getStatus().getHttpStatus());
    }

    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, Locale locale) {
        final ResponseView responseView = new ResponseView();
        for (ViewStatusCode code : viewStatus.getErrors()) {
            responseView.addError(code.getField(),
                    messageSource.getMessage(code.getMessage(), null, locale));
        }
        return new ResponseEntity<>(responseView, HttpStatus.BAD_REQUEST);
    }

    public ResponseEntity<ResponseView> makeResponse(StorageResponse response) {
        if (!response.isValid()) {
            ResponseView responseView = new ResponseView();
            responseView.addError("file", "Error upload");
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(responseView);
        }
        return ResponseEntity.ok(new ResponseView());
    }

    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, Locale locale,
                                                     String responseFielName) {
        return null;
    }

    ResponseEntity<ResponseView> makeOkUserServiceResponse(UserServiceResponse userServiceResponse,
                                                           String responseFieldName) {
        if (userServiceResponse.getData() != null) {
            return new ResponseEntity<>(new ResponseView<>(responseFieldName, userServiceResponse.getData()),
                    userServiceResponse.getStatus().getHttpStatus());
        } else {
            return new ResponseEntity<>(new ResponseView(), userServiceResponse.getStatus().getHttpStatus());
        }
    }

    ResponseEntity<ResponseView> makeErrorUserServiceResponse(UserServiceResponse userServiceResponse,
                                                              String message) {
        String errorField = "general";
        if (userServiceResponse.getStatus().getField() != null) {
            errorField = userServiceResponse.getStatus().getField();
        }
        final ResponseView responseView = new ResponseView();
        responseView.addError(errorField, message);
        return new ResponseEntity<>(responseView, userServiceResponse.getStatus().getHttpStatus());
    }

    MessageSource getMessageSource() {
        return this.messageSource;
    }
}