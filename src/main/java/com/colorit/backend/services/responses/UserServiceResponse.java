package com.colorit.backend.services.responses;

import com.colorit.backend.services.statuses.IStatus;
import com.colorit.backend.services.statuses.UserServiceStatus;

public class UserServiceResponse<T> extends AbstractServiceResponse<T> {
    public UserServiceResponse(UserServiceStatus userServiceStatus) {
        super(userServiceStatus);
    }

    @Override
    public UserServiceStatus getStatus() {
        return (UserServiceStatus) super.getStatus();
    }

    @Override
    public boolean isValid() {
        return !super.getStatus().isError();
    }

    @Override
    public void setData(T data) {
        super.setData(data);
    }

    @Override
    public void setStatus(IStatus status) {
        super.setStatus(status);
    }

    @Override
    public T getData() {
        return super.getData();
    }
}