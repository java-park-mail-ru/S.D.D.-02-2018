package com.colorit.backend.views.input;

import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public abstract class AbstractInputView {
    @SuppressWarnings("unused")
    ViewStatus checkValid() {
        return null;
    }

    static final Pattern VALID_EMAIL_ADDRESS_REGEX =
            Pattern.compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

    void checkEmail(ViewStatus viewStatus, String email) {
        if (email == null || email.isEmpty()) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_EMAIL);
        } else {
            final Matcher matcher = VALID_EMAIL_ADDRESS_REGEX.matcher(email);
            if (!matcher.find()) {
                viewStatus.addStatusCode(ViewStatusCode.INVALID_EMAIL_STATE);
            }
        }
    }

    public String getNickname() {
        return null;
    }

    public String getEmail() {
        return null;
    }

    public String getPassword() {
        return null;
    }
}
