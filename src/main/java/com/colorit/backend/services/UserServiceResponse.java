package com.colorit.backend.services;

public class UserServiceResponse extends AbstractServiceResponse {
    private UserServiceStatus status;

    @SuppressWarnings("unused")
    public UserServiceResponse() {
        super();
        status = UserServiceStatus.OK_STATE;
    }

    public UserServiceResponse(UserServiceStatus userServiceStatus) {
        super();
        this.status = userServiceStatus;
    }

    public UserServiceStatus getStatus() {
        return this.status;
    }

    public boolean isValid() {
        return this.status == UserServiceStatus.OK_STATE
                || this.status == UserServiceStatus.CREATED_STATE;
    }

    public void setStatus(UserServiceStatus userServiceStatus) {
        this.status = userServiceStatus;
    }
}
