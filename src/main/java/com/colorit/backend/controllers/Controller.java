package com.colorit.backend.controllers;

import com.colorit.backend.common.ResponseMaker;
import com.colorit.backend.services.IUserService;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public  class Controller {
    private final @NotNull IUserService userService;
    private final ResponseMaker responseMaker;
    private static final String SESSION_KEY = "nickname";

    IUserService getUserService() {
        return userService;
    }

    ResponseMaker getResponseMaker() {
        return responseMaker;
    }

    static String getSessionKey() {
        return SESSION_KEY;
    }

    public Controller(@NotNull IUserService userService,
                      @NotNull ResponseMaker responseMaker) {
        this.userService = userService;
        this.responseMaker = responseMaker;
    }
}
