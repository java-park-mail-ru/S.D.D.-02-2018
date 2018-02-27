package  com.color_it.backend.common;

import com.color_it.backend.services.UserServiceResponse;
import com.color_it.backend.services.UserServiceStatusCode;
import com.color_it.backend.views.ResponseView;
import com.color_it.backend.views.UserView;
import com.color_it.backend.views.ViewStatus;
import com.color_it.backend.views.ViewStatusCode;
//import com.sun.tools.internal.xjc.reader.xmlschema.bindinfo.BIConversion;
import org.apache.catalina.User;
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

    public AbstractResponseMaker(Map<UserServiceStatusCode, HttpStatus> hashServiceStatusAndHttpStatus) {
        this.hashServiceStatusAndHttpStatus = hashServiceStatusAndHttpStatus;
    }

    public ResponseEntity<ResponseView> makeResponse(UserServiceResponse userServiceResponse, MessageSource messageSource, Locale locale) {
        return null;
    }

//    static public ResponseEntity<ResponseView> makeResponse(UserServiceResponse response, MessageSource messageSource, Locale locale) {
//
//        ResponseView responseView = new ResponseView();
//        if (response.getUserEntity() != null) {
//            UserView userView = new UserView(response.getUserEntity());
//            responseView.setData(userView);
//        }
//
//        return  new ResponseEntity<>(responseView, HttpStatus.OK);
////        Res
////        return response.toHttpResponse();
//    }

//    static Map<ViewStatus, >ViewStatus

    public ResponseEntity<ResponseView> makeResponse(ViewStatus viewStatus, MessageSource messageSource, Locale locale) {
        ResponseView responseView = new ResponseView();
        for (ViewStatusCode code: viewStatus.getErrors()) {
            responseView.addError(hashViewErrorAndField.get(code), messageSource.getMessage(hashViewErrorAndMessage.get(code), null, locale));
        }
        return new ResponseEntity<>(responseView, HttpStatus.BAD_REQUEST);
    }


}
