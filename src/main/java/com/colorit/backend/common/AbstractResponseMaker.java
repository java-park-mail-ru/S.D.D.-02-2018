package com.colorit.backend.common;

import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import com.colorit.backend.views.output.ResponseView;
import com.colorit.backend.views.output.UserListView;
import com.colorit.backend.views.output.UserView;
import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;
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

    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, Locale locale) {
        return null;
    }

    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, Locale locale) {
        final ResponseView responseView = new ResponseView();
        for (ViewStatusCode code : viewStatus.getErrors()) {
            responseView.addError(code.getField(),
                    messageSource.getMessage(code.getMessage(), null, locale));
        }
        return new ResponseEntity<>(responseView, HttpStatus.BAD_REQUEST);
    }

    ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, String message) {
        final UserServiceStatus userServiceStatus = userServiceResponse.getStatus();
        final HttpStatus httpStatus = userServiceStatus.getHttpStatus();
        if (userServiceResponse.isValid()) {
            if (userServiceResponse.getData() != null) {
                if (userServiceResponse.getData() instanceof UserEntity) {
                    return new ResponseEntity<>(new ResponseView<>(
                            new UserView((UserEntity) userServiceResponse.getData())), httpStatus);
                } else if (userServiceResponse.getData() instanceof List) {
                    return new ResponseEntity<>(new ResponseView<>(
                            new UserListView((List) userServiceResponse.getData())), httpStatus);
                } else {
                    return new ResponseEntity<>(new ResponseView<>(userServiceResponse.getData()), httpStatus);
                }
            }
        } else {
            String field = "general";
            if (userServiceResponse.getStatus().getField() != null) {
                field = userServiceResponse.getStatus().getField();
            }
            final ResponseView responseView = new ResponseView<>();
            responseView.addError(field, message);
            return new ResponseEntity<>(responseView, httpStatus);
        }
        return new ResponseEntity<>(new ResponseView<>(), httpStatus);
    }

    MessageSource getMessageSource() {
        return this.messageSource;
    }

}