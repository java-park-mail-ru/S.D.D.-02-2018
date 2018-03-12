package com.colorit.backend.views.entity;

import java.util.List;

public class UserListView {
    private List<UserView> userViewList;
    private Integer size;

    public UserListView(List<UserView> userViews) {
        this.userViewList = userViews;
        this.size = userViews.size();
    }

    public List<UserView> getUserViewList() {
        return userViewList;
    }

    public Integer getSize() {
        return size;
    }
}
