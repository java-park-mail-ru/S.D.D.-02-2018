package com.colorit.backend.entities.db;

import com.colorit.backend.entities.input.UserUpdateEntity;
import com.colorit.backend.views.input.AbstractInputView;
import com.colorit.backend.views.entity.representations.UserEntityRepresentation;

import javax.persistence.*;

@Entity(name = "UserEntity")
@Table(name = "user_entity", uniqueConstraints = {
        @UniqueConstraint(columnNames = {"nickname"}, name = "nickname_constraint"),
        @UniqueConstraint(columnNames = {"email"}, name = "email_constraint")
})
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

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Integer getId() {
        return id;
    }

    @Column(name = "nickname")
    public String getNickname() {
        return this.nickname;
    }

    @Column(name = "email")
    public String getEmail() {
        return email;
    }

    @Column(name = "avatar_path")
    public String getAvatarPath() {
        return avatarPath;
    }

    @Column(name = "password_hash")
    public String getPasswordHash() {
        return passwordHash;
    }

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "game_results_id")
    public GameResults getGameResults() {
        return gameResults;
    }

    public void setGameResults(GameResults gameResults) {
        this.gameResults = gameResults;
    }

    public void setId(Integer id) {
        this.id = id;
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

    @Transient
    public void setRating(Integer rating) {
        this.gameResults.setRating(rating);
    }

    @Transient
    public Integer getCountGames() {
        return gameResults.getCountGames();
    }

    @Transient
    public Integer getCountWins() {
        return gameResults.getCountWins();
    }

    @Transient
    public Integer getRating() {
        return gameResults.getRating();
    }

    @Transient
    public void setCountGames(Integer countGames) {
        gameResults.setCountGames(countGames);
    }

    @Transient
    public void setCountWins(Integer countWins) {
        this.gameResults.setCountWins(countWins);
    }

    @Transient
    public void copy(UserUpdateEntity other) {
        this.nickname = other.getNewNickname() != null ? other.getNewNickname() : this.nickname;
        this.passwordHash = other.getNewPassword() != null ? other.getNewPassword() : this.passwordHash;
        this.email = other.getNewEmail() != null ? other.getNewEmail() : this.email;
    }

    @Transient
    public UserEntityRepresentation toRepresentation() {
        return new UserEntityRepresentation(nickname, email, avatarPath, gameResults.getRating(),
                gameResults.getCountWins(), gameResults.getCountGames());
    }

    @Transient
    public static UserEntity fromView(AbstractInputView view) {
        return new UserEntity(
                view.getNickname(),
                view.getEmail(),
                view.getPassword()
        );
    }
}