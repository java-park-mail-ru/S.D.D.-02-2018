package com.colorit.backend.controllers;

import com.colorit.backend.common.UserResponseMaker;
import com.colorit.backend.entities.UserUpdateEntity;
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
    private final IUserService userService;

    @Autowired
    public UserController(@NotNull IUserService userService,
                          @NotNull UserResponseMaker userResponseMaker,
                          @NotNull FileStorage fileStorage) {
        super(userResponseMaker);
        this.userService = userService;
        this.fileStorage = fileStorage;
    }


    /**
     * Updates avatar of current user
     * @param avatar - user avatar
     * @param httpSession - current session
     * @param locale - user locale
     * @return ResponseEntity
     */
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

        UserServiceResponse userServiceResponse = userService.updateAvatar(sessionNickname,
                (String) storageResponse.getData());
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }


    /**
     * upates all field, which contains user (some of them can be empty - no update)
     * @param updateView - view, which contains fields, need to update
     * @param httpSession - current session
     * @param locale - user locale
     * @return ResponseEntity
     */
    @PostMapping(path = "/update", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> update(@RequestBody UpdateView updateView,
                                               HttpSession httpSession, Locale locale) {

        final String sessionNickname = (String) httpSession.getAttribute(getSessionKey());
        if (sessionNickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        final ViewStatus viewStatus = updateView.checkValid();
        if (viewStatus.isNotValid()) {
            return getResponseMaker().makeResponse(viewStatus, locale);
        }

        final UserUpdateEntity updateEntity = updateView.toEntity();
        UserServiceResponse userServiceResponse = userService.update(sessionNickname, updateEntity);
        if (userServiceResponse.isValid()) {
            if (updateEntity.getNewNickname() != null) {
                httpSession.setAttribute(getSessionKey(), updateEntity.getNewNickname());
            }
        }
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }


    /**
     * updates email of current user
     * @param updateEmailView - view, which contains new email
     * @param httpSession - current session
     * @param locale - user locale
     * @return ResponseEntity
     */
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

        final UserServiceResponse userServiceResponse = userService.updateEmail(nickname, updateEmailView.getNewEmail());
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }


    /**
     * Updates password of current user
     * @param updatePasswordView - view, which contains oldpassword and newpassword
     * @param httpSession - current session
     * @param locale - user locale
     * @return ResponseEntity
     */
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

        final UserServiceResponse userServiceResponse = userService.updatePassword(nickname,
                updatePasswordView.getOldPassword(), updatePasswordView.getNewPassword());
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }


    /**
     * Get info about current user
     * @param httpSession - current session
     * @param locale - user locale
     * @return ResponseEntity
     */
    @GetMapping(path = "/info", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getCurrentUserInfo(HttpSession httpSession, Locale locale) {
        final String sessionNickname = (String) httpSession.getAttribute(getSessionKey());
        if (sessionNickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        final UserServiceResponse userServiceResponse = userService.getUser(sessionNickname);
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }


    /**
     * Get info about another user
     * @param nickname - nickname of another user
     * @param httpSession - current session
     * @param locale - user locale
     * @return ResponseEntity
     */
    @GetMapping(path = "/info/{nickname}", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getUserInfo(@PathVariable(name = "nickname") String nickname,
                                                    HttpSession httpSession, Locale locale) {
        final String sessionNickname = (String) httpSession.getAttribute(getSessionKey());
        if (sessionNickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        UserServiceResponse userServiceResponse = userService.userExists(sessionNickname);
        if (!userServiceResponse.isValid()) {
            return getResponseMaker().makeResponse(userServiceResponse, locale);
        }

        userServiceResponse = userService.getUser(nickname);
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }


    /**
     * Get users for scoreboard
     * @param limit - limit users on page
     * @param offset - offset, depend on current page
     * @param locale - user locale
     * @return ResponseEntity
     */
    @GetMapping(path = "/get_users", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getListUser(@RequestParam(name = "limit") Integer limit,
                                                    @RequestParam(name = "offset") Integer offset,
                                                    Locale locale) {
        return getResponseMaker().makeResponse(
                userService.getUsers(limit, offset), locale
        );
    }


    /**
     * returns count of users for pagination
     * @param locale - user locale
     * @return ResponseEntity
     */
    @GetMapping(path = "/get_users_count", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getCountUsers(Locale locale) {
        return getResponseMaker().makeResponse(
                userService.getUsersCount(), locale
        );
    }


    /**
     * returns user position in scoreboard
     * @param httpSession - current session
     * @param locale - user locale
     * @return ResponseEntity
     */
    @GetMapping(path = "/get_position", produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<ResponseView> getPosition(HttpSession httpSession, Locale locale) {
        final String sessionNickname = (String) httpSession.getAttribute(getSessionKey());
        if (sessionNickname == null) {
            return getResponseMaker().makeUnauthorizedResponse(locale);
        }

        UserServiceResponse userServiceResponse = userService.getPosition(sessionNickname);
        return getResponseMaker().makeResponse(userServiceResponse, locale);
    }
}