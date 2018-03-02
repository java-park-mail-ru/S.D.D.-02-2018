package com.colorit.backend.services.responses;

import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.services.statuses.UserServiceStatus;

public class UserServiceEntityResponse extends UserServiceResponse {
    private UserEntity data;

    public UserServiceEntityResponse() {
        super();
        setReturnedType(UserServiceResponse.ReturnedType.SCALAR);
    }

    public UserServiceEntityResponse(UserServiceStatus userServiceStatus) {
        super(userServiceStatus);
        setReturnedType(ReturnedType.ENTITY);
    }

    @Override
    public void setData(Object data) {
        this.data = (UserEntity) (data);
    }

    @Override
    public UserEntity getData() {
        return data;
    }
}
