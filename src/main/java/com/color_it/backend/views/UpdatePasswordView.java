package com.color_it.backend.views;

import com.color_it.backend.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdatePasswordView extends AbstractView {
    private final String oldPassword;
    private final String newPassword;
    private final String newPasswordCheck;

    @JsonCreator
    public UpdatePasswordView(@JsonProperty("oldPassword") String oldPassword,
                              @JsonProperty("newPassword") String newPassword,
                              @JsonProperty("newPasswordCheck") String newPasswordCheck) {

        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }
    public String getOldPassword() {
        return oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    @Override
    public ViewStatus checkValid() {
        final ViewStatus viewStatus = new ViewStatus();
        if (oldPassword == null || oldPassword.equals("")) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_OLD_PASSWORD);
        }

        boolean passwordFilled = true;
        if (newPassword == null || newPassword.equals("")) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_PASSWORD);
            passwordFilled = false;
        }
        if (newPasswordCheck == null || newPasswordCheck.equals("")) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_PASSWORD_CHECK);
            passwordFilled = false;
        }

        if (passwordFilled && !newPasswordCheck.equals(newPassword)) {
            viewStatus.addStatusCode(ViewStatusCode.PASSWORD_NOT_MATCH_STATE);
        }
        return viewStatus;
    }

    public UserEntity toEntity() {
        return null;
    }
}
