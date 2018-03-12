package com.colorit.backend.views.output;

import java.util.List;

public class UserListView implements IOutputView {
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
