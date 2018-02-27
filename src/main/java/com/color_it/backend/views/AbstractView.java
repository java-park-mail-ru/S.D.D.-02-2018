package com.color_it.backend.views;

import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;

public class AbstractView {
    ViewStatus checkValid() {return null;}

    public void checkEmail(ViewStatus viewStatus, String email) {
        if (email == null || email.equals("")) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_EMAIL);
        }
        else {
            try {
                final InternetAddress emailAddress = new InternetAddress(email);
                emailAddress.validate();
            } catch (AddressException e) {
                viewStatus.addStatusCode(ViewStatusCode.INVALID_EMAIL_STATE);
            }
        }
    }
}
