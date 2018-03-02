package com.colorit.backend.services.responses;

import com.colorit.backend.services.statuses.IStatus;
import com.colorit.backend.services.statuses.UserServiceStatus;

public class UserServiceResponse<T> implements AbstractServiceResponse {
    private UserServiceStatus serviceStatus;
    private ReturnedType returnedType;
    private T data;

    public enum ReturnedType {
        ENTITY,
        SCALAR,
        LIST_ENTITIES
    }

    public UserServiceResponse() {
        serviceStatus = UserServiceStatus.OK_STATE;
    }

    protected void setReturnedType(ReturnedType returnedType) {
        this.returnedType = returnedType;
    }

    public ReturnedType getReturnedType() {
        return returnedType;
    }

    public UserServiceResponse(UserServiceStatus userServiceStatus) {
        this.serviceStatus = userServiceStatus;
    }

    @Override
    public void setStatus(IStatus istatus) {
        this.serviceStatus = (UserServiceStatus) istatus;
    }

    @Override
    public void setData(Object data) {
    }

    @Override
    public T getData() {
        return null;
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
