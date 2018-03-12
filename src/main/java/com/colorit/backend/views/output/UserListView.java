package com.colorit.backend.views.output;

import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.views.output.UserView;

import java.util.ArrayList;
import java.util.List;

public class UserListView {
    private List<UserView> userViewList = new ArrayList<>();
    private Integer size;

    public UserListView(List<UserEntity> userViews) {
        userViews.forEach(user -> this.userViewList.add(
                new UserView(user.getNickname(), user.getEmail(), user.getAvatarPath(),
                        user.getRating(), user.getCountWins(), user.getCountGames()
                ))
        );
        this.size = userViews.size();
    }

    public List<UserView> getUserViewList() {
        return userViewList;
    }

    public Integer getSize() {
        return size;
    }
}
