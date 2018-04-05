package com.colorit.backend.services.responses;

import com.colorit.backend.services.statuses.UserServiceStatus;

public class UserServiceResponse<T> {

    private UserServiceStatus status;
    private T data;

    public UserServiceResponse(UserServiceStatus userServiceStatus) {
        this.status = userServiceStatus;
    }

    public UserServiceResponse(UserServiceStatus userServiceStatus, T data) {
        this.status = userServiceStatus;
        this.data = data;
    }

    public UserServiceStatus getStatus() {
        return status;
    }

    public boolean isValid() {
        return !status.isError();
    }

    public void setData(T data) {
        this.data = data;
    }

    public void setStatus(UserServiceStatus status) {
        this.status = status;
    }

    public T getData() {
        return this.data;
    }
}