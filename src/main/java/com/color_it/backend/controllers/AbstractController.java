package com.color_it.backend.controllers;

import org.springframework.context.MessageSource;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.constraints.NotNull;

@RestController
public class AbstractController {
    protected static final String sessionKey = "nickname";
    @NotNull
    protected final MessageSource messageSource;
    public AbstractController(@NotNull MessageSource messageSource) {
        this.messageSource = messageSource;
    }
}
