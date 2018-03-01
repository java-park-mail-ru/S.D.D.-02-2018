package com.colorit.backend.controllers;

import com.colorit.backend.common.UserResponseMaker;
import com.colorit.backend.services.IUserService;
import com.colorit.backend.services.UserServiceResponse;
import com.colorit.backend.views.ResponseView;
import com.colorit.backend.views.UpdateEmailView;
import com.colorit.backend.views.UpdatePasswordView;
import com.colorit.backend.views.ViewStatus;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.MessageSource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Locale;


@RestController
@RequestMapping(path = "/api/user/")
public class UserController extends AbstractController {

    @Autowired
    public UserController(@NotNull  MessageSource messageSource, @NotNull IUserService userService,
                          @NotNull UserResponseMaker userResponseMaker) {
        super(messageSource, userService, userResponseMaker);
    }

    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getUserInfo(HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(getSessionKey());
        if (nickname == null) {
            return unauthorizedResponse(locale);
        }

        final UserServiceResponse userServiceResponse = getUserService().getUser(nickname);
        return getResponseMaker().makeResponse(userServiceResponse, getMessageSource(), locale);
    }

    @PostMapping(path = "/update_email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updateEmail(@RequestBody UpdateEmailView updateEmailView, HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(getSessionKey());
        if (nickname == null) {
            return unauthorizedResponse(locale);
        }

        final ViewStatus viewStatus = updateEmailView.checkValid();
        if (viewStatus.isNotValid()) {
            return getResponseMaker().makeResponse(viewStatus, getMessageSource(), locale);
        }

        final UserServiceResponse userServiceResponse = getUserService().updateEmail(nickname, updateEmailView.getNewEmail());
        return getResponseMaker().makeResponse(userServiceResponse, getMessageSource(), locale);
    }

    @PostMapping(path = "/update_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updatePassword(@RequestBody UpdatePasswordView updatePasswordView,
                                                       HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(getSessionKey());
        if (nickname == null) {
            return unauthorizedResponse(locale);
        }

        final ViewStatus viewStatus = updatePasswordView.checkValid();
        if (viewStatus.isNotValid()) {
            return getResponseMaker().makeResponse(viewStatus, getMessageSource(), locale);
        }

        final UserServiceResponse userServiceResponse = getUserService().updatePassword(nickname,
                updatePasswordView.getOldPassword(), updatePasswordView.getNewPassword());
        return getResponseMaker().makeResponse(userServiceResponse, getMessageSource(), locale);
    }
}