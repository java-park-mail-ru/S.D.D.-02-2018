package com.colorit.backend.services.responses;

import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;

public class UserServiceScalarResponse<T> extends UserServiceResponse {
    private T data;

    public UserServiceScalarResponse() {
        super();
        setReturnedType(ReturnedType.SCALAR);
    }

    public UserServiceScalarResponse(UserServiceStatus userServiceStatus) {
        super(userServiceStatus);
        setReturnedType(ReturnedType.ENTITY);
    }

    @Override
    public void setData(Object data) {
        this.data = (T) data;
    }

    @Override
    public T getData() {
        return this.data;
    }
}
