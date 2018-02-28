package com.color_it.backend.controllers;

import com.color_it.backend.common.AbstractResponseMaker;
import com.color_it.backend.common.AuthenticateResponseMaker;
import com.color_it.backend.entities.UserEntity;
import com.color_it.backend.services.UserService;
import com.color_it.backend.services.UserServiceResponse;
import com.color_it.backend.views.*;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@RestController
@RequestMapping("/api/user")
public class AuthenticateController extends AbstractController {
    public AuthenticateController(MessageSource messageSource, UserService userService) {
        super(messageSource, userService);
        this.responseMaker = new AuthenticateResponseMaker();
    }

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signInUser(@RequestBody SignInView signInView, HttpSession httpSession, Locale locale) {
        final ViewStatus check = signInView.checkValid();
        if (!check.isValid()) {
            return responseMaker.makeResponse(check, messageSource, locale);
        }

        final UserEntity userEntity = signInView.toEntity();
        final UserServiceResponse userServiceResponse = userService.authenticateUser(userEntity);
        if (userServiceResponse.isValid()) {
            httpSession.setAttribute(sessionKey, userEntity.getNickname());
        }
        return responseMaker.makeResponse(userServiceResponse, messageSource, locale);
    }

    @RequestMapping(value = "/signout", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signOut(HttpSession httpSession, Locale locale) {
        final ResponseView responseView = new ResponseView();
        final String nickname = (String)httpSession.getAttribute(sessionKey);
        if (nickname == null) {
            responseView.addError("general", messageSource.getMessage("unauthorized", null, locale));
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(responseView);
        }
        httpSession.invalidate();
        return ResponseEntity.ok(responseView);
    }

    // checked
    @PostMapping(path="/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signUpUser(@RequestBody SignUpView signUpView, HttpSession httpSession, Locale locale) {
        final ViewStatus check = signUpView.checkValid();
        if (!check.isValid()) {
            return responseMaker.makeResponse(check, messageSource, locale);
        }

        final UserEntity userEntity = signUpView.toEntity();
        final UserServiceResponse userServiceResponse = userService.createUser(userEntity);
        if (userServiceResponse.isValid()) {
            httpSession.setAttribute(sessionKey, userEntity.getNickname());
        }
        return responseMaker.makeResponse(userServiceResponse, messageSource, locale);
    }
}
