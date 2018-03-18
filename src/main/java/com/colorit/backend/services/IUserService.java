package com.colorit.backend.services;

import com.colorit.backend.entities.input.UserUpdateEntity;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.views.input.SignInView;
import com.colorit.backend.views.input.SignUpView;

public interface IUserService {
    UserServiceResponse createUser(SignUpView userEntity);

    UserServiceResponse getUser(String nickname);

    UserServiceResponse authenticateUser(SignInView userEntity);

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