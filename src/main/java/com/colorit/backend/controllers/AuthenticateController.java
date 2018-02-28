package com.colorit.backend.controllers;

import com.colorit.backend.common.AuthenticateResponseMaker;
import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.services.UserServiceOnList;
import com.colorit.backend.services.UserServiceResponse;
import com.colorit.backend.views.ResponseView;
import com.colorit.backend.views.SignInView;
import com.colorit.backend.views.SignUpView;
import com.colorit.backend.views.ViewStatus;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Locale;

@RestController
@RequestMapping("/api/user")
public class AuthenticateController extends AbstractController {
    public AuthenticateController(MessageSource messageSource, UserServiceOnList userService) {
        super(messageSource, userService);
        setResponseMaker(new AuthenticateResponseMaker());
    }

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signInUser(@RequestBody SignInView signInView, HttpSession httpSession, Locale locale) {
        final ViewStatus check = signInView.checkValid();
        if (check.isNotValid()) {
            return getResponseMaker().makeResponse(check, getMessageSource(), locale);
        }

        final UserEntity userEntity = signInView.toEntity();
        final UserServiceResponse userServiceResponse = getUserService().authenticateUser(userEntity);
        if (userServiceResponse.isValid()) {
            httpSession.setAttribute(getSessionKey(), userEntity.getNickname());
        }
        return getResponseMaker().makeResponse(userServiceResponse, getMessageSource(), locale);
    }

    @RequestMapping(value = "/signout", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signOut(HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(getSessionKey());
        if (nickname == null) {
            return unauthorizedResponse(locale);
        }
        httpSession.invalidate();
        return ResponseEntity.ok(new ResponseView());
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signUpUser(@RequestBody SignUpView signUpView, HttpSession httpSession, Locale locale) {
        final ViewStatus check = signUpView.checkValid();
        if (check.isNotValid()) {
            return getResponseMaker().makeResponse(check, getMessageSource(), locale);
        }

        final UserEntity userEntity = signUpView.toEntity();
        final UserServiceResponse userServiceResponse = getUserService().createUser(userEntity);
        if (userServiceResponse.isValid()) {
            httpSession.setAttribute(getSessionKey(), userEntity.getNickname());
        }
        return getResponseMaker().makeResponse(userServiceResponse, getMessageSource(), locale);
    }
}
