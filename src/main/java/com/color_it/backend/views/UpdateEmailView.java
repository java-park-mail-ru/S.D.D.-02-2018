package com.color_it.backend.views;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateEmailView extends AbstractView {
    private final String newEmail;

    @JsonCreator
    public UpdateEmailView(@JsonProperty("newEmail") String newEmail) {
        this.newEmail = newEmail;
    }

    public String getNewEmail() {
        return newEmail;
    }

    @Override
    public ViewStatus checkValid() {
        ViewStatus viewStatus = new ViewStatus();
        checkEmail(viewStatus, newEmail);
        return viewStatus;
    }
}
