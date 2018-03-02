package com.colorit.backend.controllers;

import com.colorit.backend.common.AbstractResponseMaker;
import com.colorit.backend.services.IUserService;
import com.colorit.backend.views.output.ResponseView;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
public class AbstractController {
    private final @NotNull IUserService userService;
    private final AbstractResponseMaker responseMaker;
    private static final String SESSION_KEY = "nickname";

    IUserService getUserService() {
        return userService;
    }

    AbstractResponseMaker getResponseMaker() {
        return responseMaker;
    }

    static String getSessionKey() {
        return SESSION_KEY;
    }

    public AbstractController(@NotNull IUserService userService,
                              @NotNull AbstractResponseMaker abstractResponseMaker) {
        this.userService = userService;
        this.responseMaker = abstractResponseMaker;
    }
}
