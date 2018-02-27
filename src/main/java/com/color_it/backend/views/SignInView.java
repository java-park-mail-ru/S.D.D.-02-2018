package  com.color_it.backend.views;

import com.color_it.backend.entities.UserEntity;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;

public class SignInView extends AbstractView {
    private String nickname;
    private String password;

    @JsonCreator
    SignInView(@JsonProperty("nickname") String nickname,
               @JsonProperty("password") String password) {
        this.nickname = nickname;
        this.password = password;
    }

    @Override
    public ViewStatus checkValid() {
        final ViewStatus viewStatus = new ViewStatus();
        if (nickname == null || nickname.equals("")) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_NICKNAME);
        }

        if (password == null || password.equals("")) {
            viewStatus.addStatusCode(ViewStatusCode.EMPTY_PASSWORD);
        }
        return viewStatus;
    }

    public UserEntity toEntity() {
        final UserEntity userEntity = new UserEntity(nickname, password);
        return userEntity;
    }

}
