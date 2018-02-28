package com.colorit.backend.views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateEmailView extends AbstractView {
    private final String newEmail;

    @SuppressWarnings("unused")
    @JsonCreator
    public UpdateEmailView(@JsonProperty("newEmail") String newEmail) {
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
}
