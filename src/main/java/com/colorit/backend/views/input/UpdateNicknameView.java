package com.colorit.backend.views.input;

import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateNicknameView extends AbstractInputView {
    private String newNickname;

    @JsonCreator
    public UpdateNicknameView(@JsonProperty("nickname") String newNickname) {
        this.newNickname = newNickname;
    }

    @Override
    public ViewStatus checkValid() {
        ViewStatus viewStatus = new ViewStatus();
        if (newNickname == null || newNickname.equals("")) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_NICKNAME);
        }
        return viewStatus;
    }

    @Override
    public String getNickname() {
        return newNickname;
    }
}
