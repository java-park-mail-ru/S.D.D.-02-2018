package com.colorit.backend.views.input;

import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.views.ViewStatus;
import com.colorit.backend.views.ViewStatusCode;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class UpdateView extends AbstractView {
    private final String nickname;
    private final String email;
    private final String oldPassword;
    private final String newPassword;
    private final String newPasswordCheck;

    @JsonCreator
    public UpdateView(@JsonProperty("nickname") String nickname,
                      @JsonProperty("email") String email,
                      @JsonProperty("oldPassword") String oldPassword,
                      @JsonProperty("password") String newPassword,
                      @JsonProperty("passwordCheck") String newPasswordCheck) {
        this.nickname = nickname;
        this.email = email;
        this.oldPassword = oldPassword;
        this.newPassword = newPassword;
        this.newPasswordCheck = newPasswordCheck;
    }

    @Override
    public ViewStatus checkValid() {
        final ViewStatus viewStatus = new ViewStatus();

        if (email != null) {
            checkEmail(viewStatus, email);
        }

        boolean passwordFilled = true;
        if (oldPassword == null || oldPassword.isEmpty()) {
            passwordFilled = false;
        }

        final boolean newPasswordFilled = newPassword != null && !newPassword.isEmpty();
        final boolean newPasswordCheckFilled = newPasswordCheck != null && !newPasswordCheck.isEmpty();

        if (newPasswordCheckFilled || newPasswordFilled) {
            if (newPasswordCheckFilled && newPasswordFilled) {
                if (!newPassword.equals(newPasswordCheck)) {
                    viewStatus.addStatusCode(ViewStatusCode.PASSWORD_NOT_MATCH_STATE);
                } else {
                    if (newPasswordFilled) {
                        viewStatus.addStatusCode(ViewStatusCode.EMPTY_PASSWORD_CHECK);
                    } else {
                        viewStatus.addStatusCode(ViewStatusCode.EMPTY_PASSWORD);
                    }
                }
            }
        }
        return viewStatus;
    }

    // error
    public UserEntity toEntity() {
        return new UserEntity(nickname, email, oldPassword);
    }
}
