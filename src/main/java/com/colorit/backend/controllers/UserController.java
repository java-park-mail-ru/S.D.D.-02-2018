package com.colorit.backend.controllers;

import com.colorit.backend.common.UserResponseMaker;
import com.colorit.backend.services.UserServiceOnList;
import com.colorit.backend.services.UserServiceResponse;
import com.colorit.backend.views.ResponseView;
import com.colorit.backend.views.UpdateEmailView;
import com.colorit.backend.views.UpdatePasswordView;
import com.colorit.backend.views.ViewStatus;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Locale;


@RestController
@RequestMapping(path = "/api/user/")
public class UserController extends AbstractController {
    public UserController(MessageSource messageSource, UserServiceOnList userService) {
        super(messageSource, userService);
        this.responseMaker = new UserResponseMaker();
    }

    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getUserInfo(HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(SESSION_KEY);
        if (nickname == null) {
            return unauthorizedResponse(locale);
        }

        final UserServiceResponse userServiceResponse = userService.getUser(nickname);
        return responseMaker.makeResponse(userServiceResponse, messageSource, locale);
    }

    @PostMapping(path = "/update_email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updateEmail(@RequestBody UpdateEmailView updateEmailView, HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(SESSION_KEY);
        if (nickname == null) {
            return unauthorizedResponse(locale);
        }

        final ViewStatus viewStatus = updateEmailView.checkValid();
        if (viewStatus.isNotValid()) {
            return responseMaker.makeResponse(viewStatus, messageSource, locale);
        }

        final UserServiceResponse userServiceResponse = userService.updateEmail(nickname, updateEmailView.getNewEmail());
        return responseMaker.makeResponse(userServiceResponse, messageSource, locale);
    }

    @PostMapping(path = "/update_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updatePassword(@RequestBody UpdatePasswordView updatePasswordView,
                                                       HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(SESSION_KEY);
        if (nickname == null) {
            return unauthorizedResponse(locale);
        }

        final ViewStatus viewStatus = updatePasswordView.checkValid();
        if (viewStatus.isNotValid()) {
            return responseMaker.makeResponse(viewStatus, messageSource, locale);
        }

        final UserServiceResponse userServiceResponse = userService.updatePassword(nickname,
                updatePasswordView.getOldPassword(), updatePasswordView.getNewPassword());
        return responseMaker.makeResponse(userServiceResponse, messageSource, locale);
    }
}