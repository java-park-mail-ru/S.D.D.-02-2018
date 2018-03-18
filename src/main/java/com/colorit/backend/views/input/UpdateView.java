package com.colorit.backend.views.input;

import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UpdateView extends AbstractInputView {
    private String nickname;
    private String email;
    private String oldPasswrod;
    private String newPassword;
    private String newPasswordCheck;

    @JsonCreator
    public UpdateView(@JsonProperty("nickname") String nickname,
                      @JsonProperty("email") String email,
                      @JsonProperty("oldPassword") String oldPasswrod,
                      @JsonProperty("password") String newPassword,
                      @JsonProperty("passwordCheck") String newPasswordCheck) {
        this.nickname = nickname;
        this.email = email;
        this.oldPasswrod = oldPasswrod;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }

    @Override
    public ViewStatus checkValid() {
        final ViewStatus viewStatus = new ViewStatus();
        final boolean emptyNick = nickname == null || nickname.isEmpty();
        final boolean emptyEmail = email == null || email.isEmpty();
        final boolean emptyOldPassword = oldPasswrod == null || oldPasswrod.isEmpty();
        final boolean emptyNewPassword = newPassword == null || newPassword.isEmpty();
        final boolean emptyNewPasswordCheck = newPasswordCheck == null || newPasswordCheck.isEmpty();

        if (emptyNick && emptyEmail && emptyOldPassword && emptyNewPassword && emptyNewPasswordCheck) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_UPDATE_FORM_STATE);
            return viewStatus;
        }

        if (!emptyEmail) {
            checkEmail(viewStatus, this.email);
        }

        // OK, pasword will not update
        if (emptyNewPassword && emptyOldPassword && emptyNewPasswordCheck) {
            return viewStatus;
        }

        if (!emptyOldPassword && !emptyNewPassword && !emptyNewPasswordCheck) {
            if (!newPasswordCheck.equals(newPassword)) {
                viewStatus.addStatusCode(ViewStatusCode.PASSWORD_NOT_MATCH_STATE);
            }
        } else {
            if (emptyOldPassword) {
                viewStatus.addStatusCode(ViewStatusCode.EMPTY_OLD_PASSWORD);
            }
            if (emptyNewPassword) {
                viewStatus.addStatusCode(ViewStatusCode.EMPTY_PASSWORD);
            }
            if (emptyNewPasswordCheck) {
                viewStatus.addStatusCode(ViewStatusCode.EMPTY_PASSWORD_CHECK);
            }
        }

        return viewStatus;
    }

    @Override
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPasswrod() {
        return oldPasswrod;
    }

    @Override
    public String getPassword() {
        return oldPasswrod;
    }

    public void setOldPasswrod(String oldPasswrod) {
        this.oldPasswrod = oldPasswrod;
    }

    @Override
    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public String getNewPasswordCheck() {
        return newPasswordCheck;
    }

    public void setNewPasswordCheck(String newPasswordCheck) {
        this.newPasswordCheck = newPasswordCheck;
    }
}
