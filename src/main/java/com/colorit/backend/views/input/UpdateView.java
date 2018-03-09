package com.colorit.backend.views.input;

import com.colorit.backend.entities.UserUpdateEntity;
import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;


public class UpdateView extends AbstractView {
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
        ViewStatus viewStatus = new ViewStatus();
        boolean emptyNick = nickname == null || nickname.equals("");
        boolean emptyEmail = email == null || email.equals("");
        boolean emptyOldPassword = oldPasswrod == null || oldPasswrod.equals("");
        boolean emptyNewPassword = newPassword == null || newPassword.equals("");
        boolean emptyNewPasswordCheck = newPasswordCheck == null || newPasswordCheck.equals("");

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

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getOldPasswrod() {
        return oldPasswrod;
    }

    public void setOldPasswrod(String oldPasswrod) {
        this.oldPasswrod = oldPasswrod;
    }

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

    public UserUpdateEntity toEntity() {
        return new UserUpdateEntity(nickname, email, oldPasswrod, newPassword);
    }
}
