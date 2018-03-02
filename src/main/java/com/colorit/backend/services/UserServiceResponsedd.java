//package com.colorit.backend.services;
//
//import com.colorit.backend.entities.UserEntity;
//
//import java.util.List;
//
//public class UserServiceResponse<T> extends AbstractServiceResponse { //extends AbstractEntity> extends AbstractServiceResponse {
//    private UserServiceStatus status;
//    private List<UserEntity> entities;
//    @SuppressWarnings("unused")
//    public UserServiceResponse() {
//        super();
//        status = UserServiceStatus.OK_STATE;
//    }
//
//    public UserServiceResponse(UserServiceStatus userServiceStatus) {
//        super();
//        this.status = userServiceStatus;
//    }
//
//    public UserServiceStatus getStatus() {
//        return this.status;
//    }
//
//    public boolean isValid() {
//        return this.status == UserServiceStatus.OK_STATE
//                || this.status == UserServiceStatus.CREATED_STATE;
//    }
//
//    public List<UserEntity> getEntities() {
//        return entities;
//    }
//
//
//    public void setStatus(UserServiceStatus userServiceStatus) {
//        this.status = userServiceStatus;
//    }
//}
