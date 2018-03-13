package com.colorit.backend.controllers;

import com.colorit.backend.common.AbstractResponseMaker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.CrossOrigin;

import javax.validation.constraints.NotNull;

@RestController
@CrossOrigin(origins = {"${front_url_heroku}", "${front_url_local"}, allowCredentials = "true")
public abstract class AbstractController {
    private String data;

    private final AbstractResponseMaker responseMaker;
    private static final String SESSION_KEY = "nickname";

    AbstractResponseMaker getResponseMaker() {
        return responseMaker;
    }

    static String getSessionKey() {
        return SESSION_KEY;
    }

    @Autowired
    public AbstractController(@NotNull AbstractResponseMaker responseMaker) {
        this.responseMaker = responseMaker;
    }
}
