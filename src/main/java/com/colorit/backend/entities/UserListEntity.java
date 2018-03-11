package com.colorit.backend.entities;

import com.colorit.backend.views.output.UserListView;
import com.colorit.backend.views.output.UserView;

import java.util.ArrayList;
import java.util.List;

public class UserListEntity implements IEntity {
    private List<UserEntity> userEntityList;

    public UserListEntity(List<UserEntity> userEntities) {
        this.userEntityList = userEntities;
    }

    @Override
    public UserListView toView() {
        List<UserView> userViews = new ArrayList<>();
        this.userEntityList.forEach(userEntity -> userViews.add(
                new UserView(userEntity.getNickname(), userEntity.getEmail(),
                        userEntity.getAvatarPath(), userEntity.getRating(),
                        userEntity.getCountWins(), userEntity.getCountGames())
                )
        );
        return new UserListView(userViews);
    }
}
