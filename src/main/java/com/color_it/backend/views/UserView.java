package  com.color_it.backend.views;

import com.color_it.backend.entities.UserEntity;

/**
 * Output view
 */
public class UserView {
    private String nickname;
    private String email;
    private Double rating;

    public UserView() {}

    public UserView(UserEntity userEntity) {
        this.nickname = userEntity.getNickname();
        this.email = userEntity.getEmail();
        if (userEntity.getCountGames() != 0 && userEntity.getCountWins() != null && userEntity.getCountGames() != null) {
            this.rating = userEntity.getCountWins().doubleValue() / userEntity.getCountGames().doubleValue();
        }
        else {
            this.rating = 0.0;
        }
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
}
