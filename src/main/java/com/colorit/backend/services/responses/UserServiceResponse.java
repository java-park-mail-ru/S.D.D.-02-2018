package com.colorit.backend.services.responses;

import com.colorit.backend.entities.IEntity;
import com.colorit.backend.services.statuses.IStatus;
import com.colorit.backend.services.statuses.UserServiceStatus;

public class UserServiceResponse extends AbstractServiceResponse {
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
    public void setData(IEntity data) {
        super.setData(data);
    }

    @Override
    public void setStatus(IStatus status) {
        super.setStatus(status);
    }

    @Override
    public IEntity getData() {
        return super.getData();
    }
}