package com.colorit.backend.services;

import com.colorit.backend.entities.UserEntity;
import com.colorit.backend.repositories.UserRepositoryList;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import org.springframework.stereotype.Service;

@Service
public class UserServiceOnList implements IUserService {
    private final UserRepositoryList userRepository = new UserRepositoryList();

    @Override
    public UserServiceResponse getUser(String nickname) {
        final UserServiceResponse<UserEntity> userServiceResponse =
                new UserServiceResponse<>(UserServiceStatus.OK_STATE);
        try {
            final UserEntity userEntity = userRepository.getByNickame(nickname);
            userServiceResponse.setData(userEntity);
        } catch (UserRepositoryList.NoResultException nRx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse authenticateUser(UserEntity userEntity) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            final UserEntity existingEntity = userRepository.getByNickame(userEntity.getNickname());
            if (!existingEntity.getPasswordHash().equals(userEntity.getPasswordHash())) {
                userServiceResponse.setStatus(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
            }
        } catch (UserRepositoryList.NoResultException nRx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse createUser(UserEntity userEntity) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.CREATED_STATE);
        try {
            userRepository.save(userEntity);
        } catch (UserRepositoryList.ConstraintEmailException cEEx) {
            userServiceResponse.setStatus(UserServiceStatus.CONFLICT_EMAIL_STATE);
        } catch (UserRepositoryList.ConstraintNameException cNEx) {
            userServiceResponse.setStatus(UserServiceStatus.CONFLICT_NAME_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse updateEmail(String nickname, String email) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            userRepository.changeEmail(nickname, email);
        } catch (UserRepositoryList.NoResultException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        } catch (UserRepositoryList.ConstraintEmailException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.CONFLICT_EMAIL_STATE);
        }
        return userServiceResponse;

    }

    @Override
    public UserServiceResponse updatePassword(String nickname, String oldPassword, String newPassword) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            final UserEntity existingEntity = userRepository.getByNickame(nickname);
            if (existingEntity.getPasswordHash().equals(oldPassword)) {
                userRepository.changePassword(nickname, newPassword);
            } else {
                userServiceResponse.setStatus(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
            }
        } catch (UserRepositoryList.NoResultException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }
}
