package com.color_it.backend.common;

import com.color_it.backend.services.UserServiceResponse;
import com.color_it.backend.services.UserServiceStatusCode;
import com.color_it.backend.views.ResponseView;
import com.color_it.backend.views.ViewStatus;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

public class UserResponseMaker extends AbstractResponseMaker {
    private final static Map<UserServiceStatusCode, HttpStatus> hashServiceHttpCode = Map.ofEntries(
                    Map.entry(UserServiceStatusCode.OK_STATE, HttpStatus.OK),
                    Map.entry(UserServiceStatusCode.CONFLICT_EMAIL_STATE, HttpStatus.CONFLICT),
                    Map. entry(UserServiceStatusCode.CONFLICT_NAME_STATE, HttpStatus.CONFLICT),
                    Map. entry(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND),
                    Map. entry(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND),
                    Map.entry(UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR)
            );

    public UserResponseMaker() {
        super(Map.ofEntries());
    }


//
//            new HashMap<UserServiceStatusCode, HttpStatus>() {{
//        put(UserServiceStatusCode.OK_STATE, HttpStatus.OK);
//        put(UserServiceStatusCode.CREATED_STATE, HttpStatus.CREATED);
//        put(UserServiceStatusCode.CONFLICT_EMAIL_STATE, HttpStatus.CONFLICT);
//        put(UserServiceStatusCode.CONFLICT_NAME_STATE, HttpStatus.CONFLICT);
//        put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND);
//        put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND);
//        put(UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR);
//    }};
//
//    private final static Map<UserServiceStatusCode, String>  hashServiceStatusCodeAndMessage = Map.of(
//
//    )

//    mmutableMap.of(
//            UserServiceStatusCode.OK_STATE, HttpStatus.OK,
//            UserServiceStatusCode.CREATED_STATE, HttpStatus.CREATED,
//            UserServiceStatusCode.CONFLICT_EMAIL_STATE, HttpStatus.CONFLICT,
//            UserServiceStatusCode.CONFLICT_NAME_STATE, HttpStatus.CONFLICT,
//            UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND,
//            UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND,
//            UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR
//    );
//
//            Map.ofEntries(
//                    Map.entry(UserServiceStatusCode.OK_STATE, HttpStatus.OK),
//                    Map.entry(UserServiceStatusCode.OK_STATE, HttpStatus.OK),
//                    Map.entry(UserServiceStatusCode.CONFLICT_EMAIL_STATE, HttpStatus.CONFLICT),
//                    Map. entry(UserServiceStatusCode.CONFLICT_NAME_STATE, HttpStatus.CONFLICT),
//                    Map. entry(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND),
//                    Map. entry(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND),
//                    Map.entry(UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR)
//            );


//    private final static Map<ViewStatus, HttpStatus> hashViewHttpCode = new HashMap.ofEntries(
//            Map. entry(ViewStatus.INVALID_EMAIL_STATE, HttpStatus.BAD_REQUEST),
//            Map. entry(ViewStatus.PASSWORD_NOT_MATCH_STATE, HttpStatus.BAD_REQUEST),
//            Map. entry(ViewStatus.PHOTO_PATH_ERROR_STATE, HttpStatus.BAD_REQUEST)
//
//    );

//    private final static Map<UserServiceStatusCode, String> hashServiceHttpCode = Map.ofEntries(
//            entry(UserServiceStatusCode.OK_STATE, "ok"),
//            entry(UserServiceStatusCode.CREATED_STATE, "created"),
//            entry(UserServiceStatusCode.CONFLICT_EMAIL_STATE, "conflict_username"),
//            entry(UserServiceStatusCode.CONFLICT_NAME_STATE, "conflict_email"),
//            entry(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, "bad_request_form"),
//            entry(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, ""),
//            entry(UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR)
//    );
//
//    private final static Map<ViewStatus, HttpStatus> hashViewHttpCode = Map.ofEntries(
//            entry(ViewStatus.INVALID_EMAIL_STATE, HttpStatus.BAD_REQUEST),
//            entry(ViewStatus.PASSWORD_NOT_MATCH_STATE, HttpStatus.BAD_REQUEST),
//            entry(ViewStatus.PHOTO_PATH_ERROR_STATE, HttpStatus.BAD_REQUEST)
//
//    )

    @Override
    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, MessageSource messageSource, Locale locale) {
        return super.makeResponse(viewStatus, messageSource, locale);
    }

    @Override
    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, MessageSource messageSource, Locale locale) {
        return null;
    }

}
