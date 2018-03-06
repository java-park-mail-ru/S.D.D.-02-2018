package com.colorit.backend.views.output;

import com.colorit.backend.entities.UserEntity;

import java.util.ArrayList;
import java.util.List;

public class UserListView {
    private List<UserView> userViewList;
    private Integer size;

    public UserListView(List userEntities) {
        this.userViewList = new ArrayList<>();
        userEntities.forEach(
                 userEntity -> this.userViewList.add(new UserView((UserEntity) userEntity)));
        this.size = this.userViewList.size();
    }

    public List<UserView> getUserViewList() {
        return userViewList;
    }

    public Integer getSize() {
        return size;
    }
}
