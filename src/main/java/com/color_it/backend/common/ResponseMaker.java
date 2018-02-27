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


public class ResponseMaker {
    private final static Map<UserServiceStatusCode, HttpStatus> hashServiceHttpCode = new HashMap<UserServiceStatusCode, HttpStatus>() {{
        put(UserServiceStatusCode.OK_STATE, HttpStatus.OK);
        put(UserServiceStatusCode.CREATED_STATE, HttpStatus.CREATED);
        put(UserServiceStatusCode.CONFLICT_EMAIL_STATE, HttpStatus.CONFLICT);
        put(UserServiceStatusCode.CONFLICT_NAME_STATE, HttpStatus.CONFLICT);
        put(UserServiceStatusCode.PASSWORD_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND);
        put(UserServiceStatusCode.NAME_MATCH_ERROR_STATE, HttpStatus.NOT_FOUND);
        put(UserServiceStatusCode.DB_ERROR_STATE, HttpStatus.INTERNAL_SERVER_ERROR);
    }};

    private final static Map<ViewStatusCode, String> hashViewErrorAndField = new HashMap<ViewStatusCode, String>() {{
        put(ViewStatusCode.EMPTY_EMAIL, "email");
        put(ViewStatusCode.EMPTY_NICKNAME, "nickname");
        put(ViewStatusCode.EMPTY_OLD_PASSWORD, "oldPassword");
        put(ViewStatusCode.EMPTY_PASSWORD, "password");
        put(ViewStatusCode.EMPTY_PASSWORD_CHECK, "passwordCheck");
        put(ViewStatusCode.INVALID_EMAIL_STATE, "email");
        put(ViewStatusCode.PASSWORD_NOT_MATCH_STATE, "passwordCheck");
    }};

    private final static Map<ViewStatusCode, String> hashViewErrorAndMessage = new HashMap<ViewStatusCode, String>() {{
        put(ViewStatusCode.EMPTY_EMAIL, "email_empty");
        put(ViewStatusCode.EMPTY_NICKNAME, "nickname_empty");
        put(ViewStatusCode.EMPTY_PASSWORD, "password_empty");
        put(ViewStatusCode.EMPTY_OLD_PASSWORD, "old_password_empty");
        put(ViewStatusCode.EMPTY_PASSWORD_CHECK, "passwordCheck_empty");
        put(ViewStatusCode.INVALID_EMAIL_STATE, "email_invalid");
        put(ViewStatusCode.PASSWORD_NOT_MATCH_STATE, "passwordCheck_not_match");
    }};


//    ImmutableMap.of(
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

    static public ResponseEntity<ResponseView> makeResponse(UserServiceResponse response, MessageSource messageSource, Locale locale) {

        ResponseView responseView = new ResponseView();
        if (response.getUserEntity() != null) {
            UserView userView = new UserView(response.getUserEntity());
            responseView.setData(userView);
        }

        return  new ResponseEntity<>(responseView, HttpStatus.OK);
//        Res
//        return response.toHttpResponse();
    }

//    static Map<ViewStatus, >ViewStatus

    static public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, MessageSource messageSource, Locale locale) {
        ResponseView responseView = new ResponseView();
        for (ViewStatusCode code: viewStatus.getErrors()) {
            responseView.addError(hashViewErrorAndField.get(code), messageSource.getMessage(hashViewErrorAndMessage.get(code), null, locale));
        }
        return new ResponseEntity<>(responseView, HttpStatus.BAD_REQUEST);
    }


}
