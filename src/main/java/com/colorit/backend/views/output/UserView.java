package com.colorit.backend.views.output;

import com.colorit.backend.entities.UserEntity;

/**
 * An abstract class that represents output view.
 *
 * @author hustonMavr
 * @version 1.0
 */
@SuppressWarnings("ALL")
public class UserView implements AbstractOutputView {
    private String nickname;
    private String email;
    private String avatar;
    private Integer countWins;
    private Integer countGames;
    private Integer rating;

    public UserView() {

    }

    public UserView(String nickname, String email, String avatar, Integer rating) {
        this.nickname = nickname;
    }

    public UserView(UserEntity userEntity) {
        this.nickname = userEntity.getNickname();
        this.email = userEntity.getEmail();
        this.rating = userEntity.getRating();
        this.avatar = userEntity.getAvatarPath(); // todo set pat
        this.countGames = userEntity.getCountGames();
        this.countWins = userEntity.getCountWins();
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

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }

    public Integer getCountGames() {
        return countGames;
    }

    public Integer getCountWins() {
        return countWins;
    }

    public String getAvatar() {
        return avatar;
    }
}
