package com.colorit.backend.controllers;

import com.colorit.backend.common.UserResponseMaker;
import com.colorit.backend.services.IUserService;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.storages.FileStorage;
import com.colorit.backend.views.output.ResponseView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.NotNull;
import java.util.Locale;

@RestController
@RequestMapping(path = "/api/user/")
public class UserInfoController extends AbstractController {
    private final IUserService userService;

    @Autowired
    public UserInfoController(@NotNull IUserService userService,
                              @NotNull UserResponseMaker userResponseMaker) {
        super(userResponseMaker);
        this.userService = userService;
    }


    /**
     * Get info about current user.
     *
     * @param httpSession - current session
     * @param locale      - user locale
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
     * Get info about another user.
     *
     * @param nickname    - nickname of another user
     * @param httpSession - current session
     * @param locale      - user locale
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
     * Get users for scoreboard.
     *
     * @param limit  - limit users on page
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
     * Returns count of users for pagination.
     *
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
     * Returns user position in scoreboard.
     *
     * @param httpSession - current session
     * @param locale      - user locale
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
