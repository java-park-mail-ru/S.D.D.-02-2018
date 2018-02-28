package  com.color_it.backend.common;

import com.color_it.backend.services.UserServiceResponse;
import com.color_it.backend.services.UserServiceStatusCode;
import com.color_it.backend.views.ResponseView;
import com.color_it.backend.views.UserView;
import com.color_it.backend.views.ViewStatus;
import com.color_it.backend.views.ViewStatusCode;
import org.springframework.context.MessageSource;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.HashMap;
import java.util.Locale;
import java.util.Map;


/**
 * Basic class responseMaker
 */

public class AbstractResponseMaker {
    private final static Map<ViewStatusCode, String> hashViewErrorAndField = Map.ofEntries(
            Map.entry(ViewStatusCode.EMPTY_EMAIL, "email"),
            Map.entry(ViewStatusCode.EMPTY_NICKNAME, "nickname"),
            Map.entry(ViewStatusCode.EMPTY_OLD_PASSWORD, "oldPassword"),
            Map.entry(ViewStatusCode.EMPTY_PASSWORD, "password"),
            Map.entry(ViewStatusCode.EMPTY_PASSWORD_CHECK, "passwordCheck"),
            Map.entry(ViewStatusCode.INVALID_EMAIL_STATE, "email"),
            Map.entry(ViewStatusCode.PASSWORD_NOT_MATCH_STATE, "passwordCheck")
    );

    private final static Map<ViewStatusCode, String> hashViewErrorAndMessage = Map.ofEntries(
            Map.entry(ViewStatusCode.EMPTY_EMAIL, "email_empty"),
            Map.entry(ViewStatusCode.EMPTY_NICKNAME, "nickname_empty"),
            Map.entry(ViewStatusCode.EMPTY_PASSWORD, "password_empty"),
            Map.entry(ViewStatusCode.EMPTY_OLD_PASSWORD, "old_password_empty"),
            Map.entry(ViewStatusCode.EMPTY_PASSWORD_CHECK, "passwordCheck_empty"),
            Map.entry(ViewStatusCode.INVALID_EMAIL_STATE, "email_invalid"),
            Map.entry(ViewStatusCode.PASSWORD_NOT_MATCH_STATE, "passwordCheck_not_match")
    );

    final protected Map<UserServiceStatusCode, HttpStatus> hashServiceStatusAndHttpStatus;
    final protected Map<UserServiceStatusCode, String> hashServiceStatusAndMessage;

    public AbstractResponseMaker() {
        this.hashServiceStatusAndHttpStatus = new HashMap<UserServiceStatusCode, HttpStatus>(){
            {
                put(UserServiceStatusCode.OK_STATE, HttpStatus.OK);
                put(UserServiceStatusCode.CREATED_STATE, HttpStatus.CREATED);
                put(UserServiceStatusCode.CONFLICT_EMAIL_STATE, HttpStatus.CONFLICT);
                put(UserServiceStatusCode.CONFLICT_NAME_STATE, HttpStatus.CONFLICT);
                put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, HttpStatus.FORBIDDEN);
                put(UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR);
            }
        };

        this.hashServiceStatusAndMessage = new HashMap<UserServiceStatusCode, String>() {
            {
                put(UserServiceStatusCode.OK_STATE, "ok");
                put(UserServiceStatusCode.CREATED_STATE, "created");
                put(UserServiceStatusCode.CONFLICT_EMAIL_STATE, "email_conflict");
                put(UserServiceStatusCode.CONFLICT_NAME_STATE, "nickname_conflict");
//                put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, "forbidden");
                put(UserServiceStatusCode.DB_ERROR_STATE, "server_error");
            }
        };
    }

    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, MessageSource messageSource, Locale locale) {
        final ResponseView responseView = new ResponseView();
        if (userServiceResponse.isValid()) {
            if (userServiceResponse.getUserEntity() != null) {
                responseView.setData(new UserView(userServiceResponse.getUserEntity()));
            }
        } else {
            responseView.addError("general", messageSource.getMessage(
                    hashServiceStatusAndMessage.get(userServiceResponse.getStatusCode()), null, locale));
        }
        return new ResponseEntity(responseView, hashServiceStatusAndHttpStatus.get(userServiceResponse.getStatusCode()));
    }


    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, MessageSource messageSource, Locale locale) {
        ResponseView responseView = new ResponseView();
        for (ViewStatusCode code: viewStatus.getErrors()) {
            responseView.addError(hashViewErrorAndField.get(code), messageSource.getMessage(hashViewErrorAndMessage.get(code), null, locale));
        }
        return new ResponseEntity<>(responseView, HttpStatus.BAD_REQUEST);
    }
}