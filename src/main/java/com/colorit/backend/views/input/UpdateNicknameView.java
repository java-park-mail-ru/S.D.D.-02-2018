package com.colorit.backend.views.input;

import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateNicknameView extends AbstractInputView {
    private final String newNickname;

    @JsonCreator
    public UpdateNicknameView(@JsonProperty("nickname") String newNickname) {
        this.newNickname = newNickname;
    }

    @Override
    public ViewStatus checkValid() {
        final ViewStatus viewStatus = new ViewStatus();
        if (newNickname == null || newNickname.isEmpty()) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_NICKNAME);
        }
        return viewStatus;
    }

    @Override
    public String getNickname() {
        return newNickname;
    }
}
