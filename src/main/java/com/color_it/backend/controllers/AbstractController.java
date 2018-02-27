package com.color_it.backend.controllers;

import com.color_it.backend.common.AbstractResponseMaker;
import com.color_it.backend.services.UserService;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class AbstractController {
    @NotNull
    protected final MessageSource messageSource;
    @NotNull
    protected final UserService userService;
    protected AbstractResponseMaker responseMaker;
    protected static final String sessionKey = "nickname";

    public AbstractController(@NotNull MessageSource messageSource, @NotNull UserService userService) {
        this.messageSource = messageSource;
        this.userService = userService;
    }
}
