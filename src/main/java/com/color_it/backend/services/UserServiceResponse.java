package com.color_it.backend.services;

public class UserServiceResponse extends AbstractServiceResponse {
    private UserServiceStatusCode statusCode;

    public UserServiceResponse() {
        super();
        statusCode = UserServiceStatusCode.OK_STATE;
    }

    public UserServiceResponse(UserServiceStatusCode statusCode) {
        super();
        this.statusCode = statusCode;
    }

    public UserServiceStatusCode getStatusCode() {
        return statusCode;
    }

    public boolean isValid() {
        return this.statusCode == UserServiceStatusCode.OK_STATE
                || this.statusCode == UserServiceStatusCode.CREATED_STATE;
    }

    public void setStatusCode(UserServiceStatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
