package com.colorit.backend.views.input;

import com.colorit.backend.entities.UserUpdateEntity;
import com.colorit.backend.views.ViewStatus;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateView extends AbstractView {
    private String nickname;
    private String emial;
    private String oldPasswrod;
    private String newPassword;
    private String newPasswordCheck;

    @JsonCreator
    public UpdateView(@JsonProperty("nickname") String nickname,
                      @JsonProperty("email") String emial,
                      @JsonProperty("oldPassword") String oldPasswrod,
                      @JsonProperty("newPassword") String newPassword,
                      @JsonProperty("newPasswordCheck") String newPasswordCheck) {
        this.nickname = nickname;
        this.emial = emial;
        this.oldPasswrod = oldPasswrod;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getEmial() {
        return emial;
    }

    public void setEmial(String emial) {
        this.emial = emial;
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

    public ViewStatus isValid() {
        return null;
    }

    public String getNewPasswordCheck() {
        return newPasswordCheck;
    }

    public void setNewPasswordCheck(String newPasswordCheck) {
        this.newPasswordCheck = newPasswordCheck;
    }

    public UserUpdateEntity toEntity() {
        return new UserUpdateEntity();
    }
}
