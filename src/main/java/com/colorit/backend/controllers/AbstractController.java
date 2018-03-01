package com.colorit.backend.controllers;

import com.colorit.backend.common.AbstractResponseMaker;
import com.colorit.backend.services.IUserService;
import com.colorit.backend.views.ResponseView;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
public class AbstractController {
    private final @NotNull MessageSource messageSource;
    private final @NotNull IUserService userService;
    private AbstractResponseMaker responseMaker;
    private static final String SESSION_KEY = "nickname";

    protected ResponseEntity<ResponseView> unauthorizedResponse(Locale locale) {
        final ResponseView responseView = new ResponseView();
        responseView.addError("general", messageSource.getMessage("unauthorized", null, locale));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseView);
    }

    public MessageSource getMessageSource() {
        return messageSource;
    }

    public IUserService getUserService() {
        return userService;
    }

    public AbstractResponseMaker getResponseMaker() {
        return responseMaker;
    }

    public static String getSessionKey() {
        return SESSION_KEY;
    }

    public AbstractController(@NotNull MessageSource messageSource, @NotNull IUserService userService,
                              @NotNull AbstractResponseMaker abstractResponseMaker) {
        this.messageSource = messageSource;
        this.userService = userService;
        this.responseMaker = abstractResponseMaker;
    }
}
