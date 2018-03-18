package com.colorit.backend.views.input;

import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInView extends AbstractInputView {
    private final String nickname;
    private final String password;

    @SuppressWarnings("unused")
    @JsonCreator
    SignInView(@JsonProperty("nickname") String nickname,
               @JsonProperty("password") String password) {
        this.nickname = nickname;
        this.password = password;
    }

    @Override
    public ViewStatus checkValid() {
        final ViewStatus viewStatus = new ViewStatus();
        if (nickname == null || nickname.isEmpty()) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_NICKNAME);
        }

        if (password == null || password.isEmpty()) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_PASSWORD);
        }
        return viewStatus;
    }

    @Override
    public String getNickname() {
        return this.nickname;
    }

    @Override
    public String getPassword() {
        return this.password;
    }
}
