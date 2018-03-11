package com.colorit.backend.views.input;

import com.colorit.backend.views.ViewStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateEmailView extends AbstractInputView {
    private final String newEmail;

    @SuppressWarnings("unused")
    @JsonCreator
    public UpdateEmailView(@JsonProperty("email") String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    @Override
    public ViewStatus checkValid() {
        final ViewStatus viewStatus = new ViewStatus();
        checkEmail(viewStatus, newEmail);
        return viewStatus;
    }

    @Override
    public String getEmail() {
        return newEmail;
    }
}
