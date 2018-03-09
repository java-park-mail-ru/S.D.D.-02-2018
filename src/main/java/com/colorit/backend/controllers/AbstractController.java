package com.colorit.backend.controllers;

import com.colorit.backend.common.AbstractResponseMaker;
import com.colorit.backend.services.IUserService;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
//@CrossOrigin(origins = "http://localhost:3000", allowCredentials = "true")
public abstract class AbstractController {
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
                              @NotNull AbstractResponseMaker responseMaker) {
        this.userService = userService;
        this.responseMaker = responseMaker;
    }
}
