package com.colorit.backend.entities;


//import javax.persistence.*;

@SuppressWarnings("unused")
//@Entity(name = "UserEntity")
//@Table(name = "user_entity", uniqueConstraints = {
        //@UniqueConstraint(columnNames = {"nickname"}, name = "nickname_constraint"),
        //@UniqueConstraint(columnNames = {"email"}, name = "email_constraint")
//})
public class UserEntity {
    private Integer id;
    private String nickname;
    private String email;
    private String passwordHash;
    private String avatarPath;

    private GameResults gameResults;

    public UserEntity() {
    }

    public UserEntity(String nickname, String email, String password) {
        this.nickname = nickname;
        this.email = email;
        this.passwordHash = password;
    }

    public UserEntity(String nickname, String password) {
        this.nickname = nickname;
        this.passwordHash = password;
    }

    //@Id
    //@GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    public void setGameResults(GameResults gameResults) {
        this.gameResults = gameResults;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //@Column(name = "nickname")
    public String getNickname() {
        return this.nickname;
    }

    //@Column(name = "avatar_path")
    public String getAvatarPath() {
        return avatarPath;
    }

    //@Column(name = "email")
    public String getEmail() {
        return email;
    }

    //@Column(name = "password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    //@OneToOne(fetch = FetchType.LAZY)
    public GameResults getGameResults() {
        return gameResults;
    }

    //@Transient
    public Double getRating() {
        if (gameResults.getCountGames() == 0) {
            return 0.0;
        }
        return gameResults.getCountWins() / gameResults.getCountGames().doubleValue();
    }

    //@Transient
    public Integer getCountWins() {
        return gameResults.getCountWins();
    }

    public void setPasswordHash(String passwordHash) {
        this.passwordHash = passwordHash;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public void setCountGames(Integer countGames) {
        gameResults.setCountGames(countGames);
    }

    public void setCountWins(Integer countWins) {
        this.gameResults.setCountWins(countWins);
    }
}