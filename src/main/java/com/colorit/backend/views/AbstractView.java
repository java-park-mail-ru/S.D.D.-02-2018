package com.colorit.backend.views;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

abstract class AbstractView {
    @SuppressWarnings("unused")
    ViewStatus checkValid() {
        return null;
    }

    void checkEmail(ViewStatus viewStatus, String email) {
        if (email == null || email.isEmpty()) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_EMAIL);
        } else {
            try {
                final InternetAddress emailAddress = new InternetAddress(email);
                emailAddress.validate();
            } catch (AddressException e) {
                viewStatus.addStatusCode(ViewStatusCode.INVALID_EMAIL_STATE);
            }
        }
    }
}
