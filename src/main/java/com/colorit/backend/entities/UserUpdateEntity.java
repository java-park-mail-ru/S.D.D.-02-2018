package com.colorit.backend.entities;

public class UserUpdateEntity {
    private String newNickname;
    private String newEmial;
    private String oldPassword;
    private String newPassword;

    public UserUpdateEntity(String newNickname, String newEmial, String oldPassword, String newPassword) {
        this.newNickname = newNickname;
        this.newEmial = newEmial;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
    }

    public String getNewNickname() {
        return newNickname;
    }

    public void setNewNickname(String newNickname) {
        this.newNickname = newNickname;
    }

    public String getNewEmial() {
        return newEmial;
    }

    public void setNewEmial(String newEmial) {
        this.newEmial = newEmial;
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
}
