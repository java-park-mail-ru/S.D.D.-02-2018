package com.colorit.backend.services.responses;

import com.colorit.backend.services.statuses.UserServiceStatus;

import java.util.List;

public class UserServiceCollectionResponse extends UserServiceResponse {
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
        super.setData(data);
    }

    @Override
    public Object getData() {
        return super.getData();
    }
}
