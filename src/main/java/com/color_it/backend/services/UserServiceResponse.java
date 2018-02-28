package com.color_it.backend.services;

import com.color_it.backend.entities.UserEntity;
import org.springframework.http.ResponseEntity;

public class UserServiceResponse extends AbstractServiceResponse {
    UserEntity entity;
//    List<UserEntity>
    UserServiceStatusCode statusCode;

    public UserServiceResponse() {
        statusCode = UserServiceStatusCode.OK_STATE;
    }

    public UserServiceResponse(UserServiceStatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public UserEntity getUserEntity() {
        return this.entity;
    }

    public void setEntity(UserEntity userEntity) {
        this.entity = userEntity;
    }

    public UserServiceStatusCode getStatusCode() {
        return statusCode;
    }

    public boolean isValid() {
        return this.statusCode == UserServiceStatusCode.OK_STATE ||
               this.statusCode == UserServiceStatusCode.CREATED_STATE;
    }

    public void setStatusCode(UserServiceStatusCode statusCode) {
        this.statusCode = statusCode;
    }
}
