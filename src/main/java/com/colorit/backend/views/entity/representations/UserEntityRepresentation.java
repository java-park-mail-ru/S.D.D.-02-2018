package com.colorit.backend.views.entity.representations;


import com.colorit.backend.entities.db.UserEntity;

/**
 * An abstract class that represents output view.
 *
 * @author hustonMavr
 * @version 1.0
 */
@SuppressWarnings("ALL")
public class UserEntityRepresentation {
    private String nickname;
    private String email;
    private String avatar;
    private Integer countWins;
    private Integer countGames;
    private Integer rating;

    public UserEntityRepresentation() {

    }

    public UserEntityRepresentation(String nickname, String email, String avatar,
                                    Integer rating, Integer countWins, Integer countGames) {
        this.nickname = nickname;
        this.email = email;
        this.avatar = avatar;
        this.countWins = countWins;
        this.rating = rating;
        this.countGames = countGames;
    }

    public UserEntityRepresentation(UserEntity userEntity) {
        this.nickname = userEntity.getNickname();
        this.email = userEntity.getEmail();
        this.avatar = userEntity.getAvatarPath();
        this.countWins = userEntity.getCountWins();
        this.rating = userEntity.getRating();
        this.countGames = userEntity.getCountGames();
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
