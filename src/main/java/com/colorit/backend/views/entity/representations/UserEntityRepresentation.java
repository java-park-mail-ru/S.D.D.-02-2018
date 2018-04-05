package com.colorit.backend.views.entity.representations;


/**
 * An abstract class that represents output view.
 *
 * @author hustonMavr
 * @version 1.0
 */
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

    @SuppressWarnings("unused")
    public Integer getRating() {
        return rating;
    }

    @SuppressWarnings("unused")
    public void setRating(Integer rating) {
        this.rating = rating;
    }

    @SuppressWarnings("unused")
    public Integer getCountGames() {
        return countGames;
    }

    @SuppressWarnings("unused")
    public Integer getCountWins() {
        return countWins;
    }

    @SuppressWarnings("unused")
    public String getAvatar() {
        return avatar;
    }
}
