package com.colorit.backend.controllers;

import com.colorit.backend.common.UserResponseMaker;
import com.colorit.backend.services.IUserService;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.storages.FileStorage;
import com.colorit.backend.storages.responses.AbstractStorageResponse;
import com.colorit.backend.views.*;
import com.colorit.backend.views.input.UpdateEmailView;
import com.colorit.backend.views.input.UpdatePasswordView;
import com.colorit.backend.views.input.UpdateView;
import com.colorit.backend.views.output.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Locale;


@RestController
@RequestMapping(path = "/api/user/")
public class UserController extends AbstractController {
    private FileStorage fileStorage;

    @Autowired
    public UserController(@NotNull IUserService userService,
                          @NotNull UserResponseMaker userResponseMaker,
                          @NotNull FileStorage fileStorage) {
        super(userService, userResponseMaker);
        this.fileStorage = fileStorage;
    }

    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getCurrentUserInfo(@PathVariable(name = "nickname") String nickname,
                                                    HttpSession httpSession, Locale locale) {

        final String sessionNickname = (String) httpSession.getAttribute(getSessionKey());
        if (sessionNickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        // todo check userexist, then get data; ??? there>
        final UserServiceResponse userServiceResponse = getUserService().getUser(
                nickname != null ? nickname : sessionNickname);
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }

    @GetMapping(path = "/info/{nickname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getUserInfo(@PathVariable(name = "nickname") String nickname,
                                                    HttpSession httpSession, Locale locale) {

        final String sessionNickname = (String) httpSession.getAttribute(getSessionKey());
        if (sessionNickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }



        // todo check userexist, then get data;
        final UserServiceResponse userServiceResponse = getUserService().getUser(
                nickname != null ? nickname : sessionNickname);
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }

    @GetMapping(path = "/get_users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getListUser(@RequestParam(name="limit") Integer limit,
                                                    @RequestParam(name="offset") Integer offset,
                                                    Locale locale) {
        return getResponseMaker().makeResponse(
                getUserService().getUsers(limit, offset), locale
        );
    }

    @GetMapping(path="/get_position", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getPosition(HttpSession httpSession, Locale locale) {
        return null;
    }

    @GetMapping(path="/get_users_count", produces=MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getCountUsers(Locale locale) {
        return getResponseMaker().makeResponse(
                getUserService().getUsersCount(), locale
        );
    }

    @PostMapping(path = "/update_avatar", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updateAvatar(@RequestParam(name = "file") MultipartFile avatar,
                                                     HttpSession httpSession, Locale locale) {
        final String sessionNickname = (String) httpSession.getAttribute(getSessionKey());
        if (sessionNickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        final AbstractStorageResponse storageResponse = fileStorage.saveFile(avatar);
        if (!storageResponse.isValid()) {
            return getResponseMaker().makeResponse(storageResponse);
        }

        UserServiceResponse userServiceResponse = getUserService().updateAvatar(sessionNickname,
                (String) storageResponse.getData());
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }

    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> update(@RequestBody UpdateView updateView,
                                               HttpSession httpSession, Locale locale) {
        return null;
    }



    @PostMapping(path = "/update_email", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updateEmail(@RequestBody UpdateEmailView updateEmailView, HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(getSessionKey());
        if (nickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        final ViewStatus viewStatus = updateEmailView.checkValid();
        if (viewStatus.isNotValid()) {
            return getResponseMaker().makeResponse(viewStatus, locale);
        }

        final UserServiceResponse userServiceResponse = getUserService().updateEmail(nickname, updateEmailView.getNewEmail());
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }

    @PostMapping(path = "/update_password", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> updatePassword(@RequestBody UpdatePasswordView updatePasswordView,
                                                       HttpSession httpSession, Locale locale) {
        final String nickname = (String) httpSession.getAttribute(getSessionKey());
        if (nickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        final ViewStatus viewStatus = updatePasswordView.checkValid();
        if (viewStatus.isNotValid()) {
            return getResponseMaker().makeResponse(viewStatus, locale);
        }

        final UserServiceResponse userServiceResponse = getUserService().updatePassword(nickname,
                updatePasswordView.getOldPassword(), updatePasswordView.getNewPassword());
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }
}