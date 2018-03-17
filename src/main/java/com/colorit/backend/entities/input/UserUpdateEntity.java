package com.colorit.backend.entities.input;

import com.colorit.backend.views.input.AbstractInputView;

public class UserUpdateEntity {
    private String newNickname;
    private String newEmail;
    private String oldPassword;
    private String newPassword;

    public UserUpdateEntity(String newNickname, String newEmail, String oldPassword, String newPassword) {
        this.newNickname = newNickname;
        this.newEmail = newEmail;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public void setNewNickname(String newNickname) {
        this.newNickname = newNickname;
    }

    public String getNewEmail() {
        return newEmail;
    }

    public void setNewEmail(String newEmial) {
        this.newEmail = newEmial;
    }

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }

    public void setNewPassword(String newPassword) {
        this.newPassword = newPassword;
    }

    public static UserUpdateEntity fromView(AbstractInputView inputView) {
        return new UserUpdateEntity(inputView.getNickname(), inputView.getEmail(),
                inputView.getPassword(), inputView.getNewPassword());
    }


}
