package com.colorit.backend.services;

import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.entities.input.UserUpdateEntity;
import com.colorit.backend.services.responses.UserServiceResponse;

public interface IUserService {
    UserServiceResponse createUser(UserEntity userEntity);

    UserServiceResponse getUser(String nickname);

    UserServiceResponse authenticateUser(UserEntity userEntity);

    UserServiceResponse updateNickname(String nickname, String newNickname);

    UserServiceResponse updateEmail(String nickname, String email);

    UserServiceResponse updatePassword(String nickname, String oldPassword, String newPassword);

    UserServiceResponse updateAvatar(String nickname, String avatar);

    UserServiceResponse update(String nickname, UserUpdateEntity updateEntity);

    UserServiceResponse getUsersCount();

    UserServiceResponse getPosition(String nickname);

    UserServiceResponse getUsers(Integer limit, Integer offset);

    UserServiceResponse userExists(String nickname);
}