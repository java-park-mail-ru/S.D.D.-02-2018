package com.color_it.backend.controllers;

import com.color_it.backend.common.AbstractResponseMaker;
import com.color_it.backend.services.UserService;
import com.color_it.backend.views.ResponseView;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
public class AbstractController {
    @NotNull
    protected final MessageSource messageSource;
    @NotNull
    protected final UserService userService;
    protected AbstractResponseMaker responseMaker;
    protected static final String sessionKey = "nickname";

    protected ResponseEntity<ResponseView> UnauthorizedResponse(Locale locale) {
        ResponseView responseView = new ResponseView();
        responseView.addError("general", messageSource.getMessage("unauthorized", null, locale));
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseView);
    }
    public AbstractController(@NotNull MessageSource messageSource, @NotNull UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }
}
