package com.colorit.backend.views.entity.representations;

import com.colorit.backend.entities.db.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserListEntityRepresentation {
    private final List<UserEntityRepresentation> userViewList = new ArrayList<>();
    private final Integer size;

    public UserListEntityRepresentation(List<UserEntity> userViews) {
        userViews.forEach(user -> this.userViewList.add(
                new UserEntityRepresentation(user.getNickname(), user.getEmail(), user.getAvatarPath(),
                        user.getRating(), user.getCountWins(), user.getCountGames()
                ))
        );
        this.size = userViews.size();
    }

    public List<UserEntityRepresentation> getUserViewList() {
        return userViewList;
    }

    public Integer getSize() {
        return size;
    }
}
