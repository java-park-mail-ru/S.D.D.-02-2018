package com.color_it.backend.controllers;

import com.color_it.backend.common.AbstractResponseMaker;
import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class AbstractController {
    @NotNull
    protected final MessageSource messageSource;
    protected AbstractResponseMaker responseMaker;
    protected static final String sessionKey = "nickname";

    public AbstractController(@NotNull MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
