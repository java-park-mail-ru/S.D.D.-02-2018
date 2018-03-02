package com.colorit.backend.services.responses;

import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.services.statuses.UserServiceStatus;

import java.util.List;

public class UserServiceCollectionResponse extends UserServiceResponse {
    List<UserEntity> data;

    UserServiceCollectionResponse() {
        super();
        setReturnedType(ReturnedType.LIST_ENTITIES);
    }

    public UserServiceCollectionResponse(UserServiceStatus userServiceStatus) {
        super(userServiceStatus);
        setReturnedType(ReturnedType.ENTITY);
    }


    @Override
    public void setData(Object data) {
        this.data = (List<UserEntity>) data;
    }

    @Override
    public List<UserEntity> getData() {
        return data;
    }
}
