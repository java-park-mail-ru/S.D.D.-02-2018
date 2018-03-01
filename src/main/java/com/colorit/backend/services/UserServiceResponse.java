package com.colorit.backend.services;

public class UserServiceResponse extends AbstractServiceResponse {
//    private UserServiceStatusCode statusCode;
    private UserServiceStatus status;

    @SuppressWarnings("unused")
    public UserServiceResponse() {
        super();
        status = UserServiceStatus.OK_STATE;
//        statusCode = UserServiceStatusCode.OK_STATE;
    }

    public UserServiceResponse(UserServiceStatus userServiceStatus) {
        super();
        this.status = userServiceStatus;
    }

//    public UserServiceResponse(UserServiceStatusCode statusCode) {
//        super();
//        this.statusCode = statusCode;
//    }

    public UserServiceStatus getStatus() {
        return this.status;
    }

//    public UserServiceStatusCode getStatusCode() {
//        return statusCode;
//    }

    public boolean isValid() {
        return this.status == UserServiceStatus.OK_STATE
                || this.status == UserServiceStatus.CREATED_STATE;
    }

//    public boolean isValid() {
//        return this.statusCode == UserServiceStatusCode.OK_STATE
//                || this.statusCode == UserServiceStatusCode.CREATED_STATE;
//    }
    public void setStatus(UserServiceStatus userServiceStatus) {
        this.status = userServiceStatus;
    }

//    public void setStatusCode(UserServiceStatusCode statusCode) {
//        this.statusCode = statusCode;
//    }
}
