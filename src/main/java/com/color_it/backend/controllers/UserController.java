package com.color_it.backend.controllers;

import com.color_it.backend.common.ResponseMaker;
import com.color_it.backend.entities.UserEntity;
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

    private final UserService userService;

    public UserController(MessageSource messageSource, UserService userService) {
        super(messageSource);
        this.userService = userService;
    }

    @GetMapping(path="/user_info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getUser(HttpSession httpSession, Locale locale) {
        String nickname = (String)httpSession.getAttribute(sessionKey);
        if (nickname == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseView(messageSource.getMessage("unauthorized", null, locale)));
        }

        UserServiceResponse userServiceResponse = userService.getUser(nickname);
//        if (userServiceResponse.isValid()) {
//        }

        return ResponseMaker.makeResponse(userServiceResponse, messageSource, locale);

//        return new ResponseEntity<>(new ResponseView(), HttpStatus.OK);
    }

    @PostMapping(path="update_email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updateEmail(@RequestBody UpdateEmailView updateEmailView, HttpSession httpSession, Locale locale) {
        String nickname = (String)httpSession.getAttribute(sessionKey);
        if (nickname == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseView(messageSource.getMessage("unauthorized", null, locale)));
        }

        final ViewStatus viewStatus = updateEmailView.checkValid();
        if (!viewStatus.isValid()) {
            return ResponseMaker.makeResponse(viewStatus, messageSource, locale);
        }

        // if user not found what i would return
        final UserServiceResponse userServiceResponse = userService.userExists(nickname);
        if (userServiceResponse.isValid()) {
            final UserServiceResponse updateEmailResponse = userService.updateEmail(updateEmailView.getNewEmail());
            return ResponseMaker.makeResponse(updateEmailResponse, messageSource, locale);
        }
        return ResponseMaker.makeResponse(userServiceResponse, messageSource, locale);
    }

    @PostMapping(path="update_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updatePassword(@RequestBody UpdatePasswordView updatePasswordView, HttpSession httpSession, Locale locale) {
        String nickname = (String)httpSession.getAttribute(sessionKey);
        if (nickname == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body(
                    new ResponseView(messageSource.getMessage("unauthorized", null, locale)));
        }

        final ViewStatus viewStatus = updatePasswordView.checkValid();
        if (!viewStatus.isValid()) {
            return ResponseMaker.makeResponse(viewStatus, messageSource, locale);
        }



        return null;
    }

    @GetMapping(value = "/score", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getRating() {
        return null;
    }
}
