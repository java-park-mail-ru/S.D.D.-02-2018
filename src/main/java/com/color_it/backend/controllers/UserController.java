package com.color_it.backend.controllers;

import com.color_it.backend.common.UserResponseMaker;
import com.color_it.backend.services.UserService;
import com.color_it.backend.services.UserServiceResponse;
import com.color_it.backend.views.ResponseView;
import com.color_it.backend.views.UpdateEmailView;
import com.color_it.backend.views.UpdatePasswordView;
import com.color_it.backend.views.ViewStatus;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import java.util.Locale;


// update email, nick, password, avatar
@RestController
@RequestMapping(path="/api/user/")
public class UserController extends AbstractController {
    public UserController(MessageSource messageSource, UserService userService) {
        super(messageSource, userService);
        this.responseMaker = new UserResponseMaker();
    }

    @GetMapping(path="/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getUser(HttpSession httpSession, Locale locale) {
        final String nickname = (String)httpSession.getAttribute(sessionKey);
        if (nickname == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseView(messageSource.getMessage("unauthorized", null, locale)));
        }

        UserServiceResponse userServiceResponse = userService.getUser(nickname);
        return responseMaker.makeResponse(userServiceResponse, messageSource, locale);
    }

    @PostMapping(path="update_email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updateEmail(@RequestBody UpdateEmailView updateEmailView, HttpSession httpSession, Locale locale) {
        final String nickname = (String)httpSession.getAttribute(sessionKey);
        if (nickname == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseView(messageSource.getMessage("unauthorized", null, locale)));
        }

        final ViewStatus viewStatus = updateEmailView.checkValid();
        if (!viewStatus.isValid()) {
            return responseMaker.makeResponse(viewStatus, messageSource, locale);
        }

        // if user not found what i would return
        final UserServiceResponse userServiceResponse = userService.userExists(nickname);
        if (userServiceResponse.isValid()) {
            final UserServiceResponse updateEmailResponse = userService.updateEmail(updateEmailView.getNewEmail());
            return responseMaker.makeResponse(updateEmailResponse, messageSource, locale);
        }
        return responseMaker.makeResponse(userServiceResponse, messageSource, locale);
    }

    @PostMapping(path="update_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updatePassword(@RequestBody UpdatePasswordView updatePasswordView, HttpSession httpSession, Locale locale) {
        final String nickname = (String)httpSession.getAttribute(sessionKey);
        if (nickname == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseView(messageSource.getMessage("unauthorized", null, locale)));
        }

        final ViewStatus viewStatus = updatePasswordView.checkValid();
        if (!viewStatus.isValid()) {
            return responseMaker.makeResponse(viewStatus, messageSource, locale);
        }

        return null;
    }



    @GetMapping(value = "/rating", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getRating(HttpSession httpSession, Locale locale) {
        final String nickname = (String)httpSession.getAttribute(sessionKey);
        if (nickname == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseView(messageSource.getMessage("unauthorized", null, locale)));
        }

        return null;
    }
}
