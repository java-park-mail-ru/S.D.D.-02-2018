package com.colorit.backend.services.responses;

import com.colorit.backend.services.statuses.IStatus;
import com.colorit.backend.services.statuses.UserServiceStatus;

public class UserServiceResponse<T> implements IServiceResponse<T> {
    private UserServiceStatus serviceStatus;
    private T data;

    public UserServiceResponse() {
        serviceStatus = UserServiceStatus.OK_STATE;
    }

    public UserServiceResponse(UserServiceStatus userServiceStatus) {
        this.serviceStatus = userServiceStatus;
    }

    @Override
    public void setStatus(IStatus istatus) {
        this.serviceStatus = (UserServiceStatus) istatus;
    }

    @Override
    public void setData(T data) {
        this.data = data;
    }

    @Override
    public T getData() {
        return this.data;
    }

    @Override
    public UserServiceStatus getStatus() {
        return serviceStatus;
    }

    @Override
    public boolean isValid() {
        return this.serviceStatus == UserServiceStatus.OK_STATE
                || this.serviceStatus == UserServiceStatus.CREATED_STATE;
    }
}
