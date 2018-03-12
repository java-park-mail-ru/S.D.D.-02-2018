package com.colorit.backend.controllers;

import com.colorit.backend.common.AuthenticateResponseMaker;
import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.services.IUserService;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.views.output.ResponseView;
import com.colorit.backend.views.input.SignInView;
import com.colorit.backend.views.input.SignUpView;
import com.colorit.backend.views.ViewStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
@RequestMapping("/api/user")
public class AuthenticateController extends AbstractController {
    private final IUserService userService;

    @Autowired
    public AuthenticateController(@NotNull IUserService userService,
                                  @NotNull AuthenticateResponseMaker authenticateResponseMaker) {
        super(authenticateResponseMaker);
        this.userService = userService;
    }

    @PostMapping(path = "/signin", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signInUser(@RequestBody SignInView signInView, HttpSession httpSession,
                                                   Locale locale) {
        final ViewStatus check = signInView.checkValid();
        if (check.isNotValid()) {
            return getResponseMaker().makeResponse(check, locale);
        }

        final UserEntity userEntity = UserEntity.fromView(signInView);
        final UserServiceResponse userServiceResponse = userService.authenticateUser(userEntity);

        if (!userServiceResponse.isValid()) {
            return getResponseMaker().makeResponse(userServiceResponse, locale);
        }
        httpSession.setAttribute(getSessionKey(), signInView.getNickname());
        return getResponseMaker().authorizedResponse(userServiceResponse, httpSession, "sessionId");
    }

    @RequestMapping(value = "/signout", method = {RequestMethod.GET, RequestMethod.POST},
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signOut(HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(getSessionKey());
        if (nickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        httpSession.invalidate();
        return ResponseEntity.ok(new ResponseView());
    }

    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> signUpUser(@RequestBody SignUpView signUpView, HttpSession httpSession, Locale locale) {
        final ViewStatus check = signUpView.checkValid();
        if (check.isNotValid()) {
            return getResponseMaker().makeResponse(check, locale);
        }

        final UserEntity userEntity = UserEntity.fromView(signUpView);
        final UserServiceResponse userServiceResponse = userService.createUser(userEntity);
        if (!userServiceResponse.isValid()) {
            return getResponseMaker().makeResponse(userServiceResponse, locale);
        }
        httpSession.setAttribute(getSessionKey(), userEntity.getNickname());
        return getResponseMaker().authorizedResponse(userServiceResponse, httpSession, "sessionId");
    }
}
