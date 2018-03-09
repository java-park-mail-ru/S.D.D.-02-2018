package com.colorit.backend.views.output;

import com.colorit.backend.entities.UserEntity;

/**
 * An abstract class that represents output view.
 *
 * @author hustonMavr
 * @version 1.0
 */
@SuppressWarnings("ALL")
public class UserView {
    private String nickname;
    private String email;
    private Double rating;
    private String avatar;

    public UserView() {

    }

    public UserView(UserEntity userEntity) {
        this.nickname = userEntity.getNickname();
        this.email = userEntity.getEmail();
        this.rating = userEntity.getRating();
        // todo
        this.avatar = userEntity.getAvatarPath();
    }

    public String getNickname() {
        return this.nickname;
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

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getAvatar() {
        return avatar;
    }
}
