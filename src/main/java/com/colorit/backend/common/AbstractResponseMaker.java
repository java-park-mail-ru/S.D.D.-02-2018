package com.colorit.backend.common;

import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.services.UserServiceResponse;
import com.colorit.backend.services.UserServiceStatusCode;
import com.colorit.backend.views.ResponseView;
import com.colorit.backend.views.UserView;
import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

/**
 * An basic class that represents responsemaker.
 *
 * @author HaseProgram - Dmitry Zaitsev
 * @version 1.0
 */
public class AbstractResponseMaker {
    protected static final Map<ViewStatusCode, String> HASH_VIEW_ERROR_AND_FIELD = Map.ofEntries(
            Map.entry(ViewStatusCode.EMPTY_EMAIL, "email"),
            Map.entry(ViewStatusCode.EMPTY_NICKNAME, "nickname"),
            Map.entry(ViewStatusCode.EMPTY_OLD_PASSWORD, "oldPassword"),
            Map.entry(ViewStatusCode.EMPTY_PASSWORD, "password"),
            Map.entry(ViewStatusCode.EMPTY_PASSWORD_CHECK, "passwordCheck"),
            Map.entry(ViewStatusCode.INVALID_EMAIL_STATE, "email"),
            Map.entry(ViewStatusCode.PASSWORD_NOT_MATCH_STATE, "passwordCheck")
    );

    protected static final Map<ViewStatusCode, String> HASH_VIEW_ERROR_AND_MESSAGE = Map.ofEntries(
            Map.entry(ViewStatusCode.EMPTY_EMAIL, "email_empty"),
            Map.entry(ViewStatusCode.EMPTY_NICKNAME, "nickname_empty"),
            Map.entry(ViewStatusCode.EMPTY_PASSWORD, "password_empty"),
            Map.entry(ViewStatusCode.EMPTY_OLD_PASSWORD, "old_password_empty"),
            Map.entry(ViewStatusCode.EMPTY_PASSWORD_CHECK, "passwordCheck_empty"),
            Map.entry(ViewStatusCode.INVALID_EMAIL_STATE, "email_invalid"),
            Map.entry(ViewStatusCode.PASSWORD_NOT_MATCH_STATE, "passwordCheck_not_match")
    );

    protected static final Map<UserServicestatusCode, HttpStatus> HASH_SERVICE_STATUS_AND_HTTP_STATUS = Map.ofEntries(
            Map.entry(UserServiceStatusCode.OK_STATE, HttpStatus.OK),
            Map.entry(UserServiceStatusCode.CREATED_STATE, HttpStatus.CREATED),
            Map.entry(UserServiceStatusCode.CONFLICT_EMAIL_STATE, HttpStatus.CONFLICT),
            Map.entry(UserServiceStatusCode.CONFLICT_NAME_STATE, HttpStatus.CONFLICT),
            Map.entry(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, HttpStatus.FORBIDDEN),
            Map.entry(UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR),
            Map.entry(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.FORBIDDEN),
    );

//    private final Map<UserServiceStatusCode, HttpStatus> hashServiceStatusAndHttpStatus;
//    @SuppressWarnings("CheckStyle")
    protected final Map<UserServiceStatusCode, String> hashServiceStatusAndMessage;

    @SuppressWarnings("MapReplaceableByEnumMap")
    public AbstractResponseMaker() {
//        this.hashServiceStatusAndHttpStatus = new UserServiceStatusCodeHttpStatusHashMap();

        //noinspection AnonymousInnerClassMayBeStatic,Convert2Diamond
        this.hashServiceStatusAndMessage = new EnumMap<UserServiceStatusCode, String>() {
            {
                put(UserServiceStatusCode.OK_STATE, "ok");
                put(UserServiceStatusCode.CREATED_STATE, "created");
                put(UserServiceStatusCode.CONFLICT_EMAIL_STATE, "email_conflict");
                put(UserServiceStatusCode.CONFLICT_NAME_STATE, "nickname_conflict");
                put(UserServiceStatusCode.DB_ERROR_STATE, "server_error");
            }
        };
    }

    public Map<UserServiceStatusCode, String> getHashServiceStatusAndMessage() {
        return hashServiceStatusAndMessage;
    }

    @SuppressWarnings("unchecked")
    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, MessageSource messageSource, Locale locale) {
        final ResponseView<UserView> responseView = new ResponseView();
        if (userServiceResponse.isValid()) {
            if (userServiceResponse.getEntity() != null) {
                responseView.setData(new UserView((UserEntity) userServiceResponse.getEntity()));
            }
        } else {
            responseView.addError("general", messageSource.getMessage(
                    hashServiceStatusAndMessage.get(userServiceResponse.getStatusCode()), null, locale));
        }
        return new ResponseEntity(responseView, hashServiceStatusAndHttpStatus.get(userServiceResponse.getStatusCode()));
    }


    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, MessageSource messageSource, Locale locale) {
        final ResponseView responseView = new ResponseView();
        for (ViewStatusCode code : viewStatus.getErrors()) {
            responseView.addError(HASH_VIEW_ERROR_AND_FIELD.get(code),
                    messageSource.getMessage(HASH_VIEW_ERROR_AND_MESSAGE.get(code), null, locale));
        }
        return new ResponseEntity<>(responseView, HttpStatus.BAD_REQUEST);
    }

//    private static class UserServiceStatusCodeHttpStatusHashMap extends HashMap<UserServiceStatusCode, HttpStatus> {
//        {
//            put(UserServiceStatusCode.OK_STATE, HttpStatus.OK);
//            put(UserServiceStatusCode.CREATED_STATE, HttpStatus.CREATED);
//            put(UserServiceStatusCode.CONFLICT_EMAIL_STATE, HttpStatus.CONFLICT);
//            put(UserServiceStatusCode.CONFLICT_NAME_STATE, HttpStatus.CONFLICT);
//            put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, HttpStatus.FORBIDDEN);
//            put(UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR);
//            put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.FORBIDDEN); // ?
//        }
//    }
}