package com.colorit.backend.services;

import com.colorit.backend.entities.*;
import com.colorit.backend.repositories.UserRepositoryList;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceOnList implements IUserService {
    private final UserRepositoryList userRepository = new UserRepositoryList();

    @Override
    public UserServiceResponse getUser(String nickname) {
        final UserServiceResponse userServiceResponse =
                new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            final UserEntity userEntity = userRepository.getByNickame(nickname);
            userServiceResponse.setData(userEntity);
        } catch (UserRepositoryList.NoResultException nRx) {
            userServiceResponse.setStatus(UserServiceStatus.NOT_FOUND_STATE);
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

    @Override
    public UserServiceResponse getPosition(String nickname) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            Long position = userRepository.getPosition(nickname);
            userServiceResponse.setData(new ScalarEntity<>(position));
        } catch (UserRepositoryList.NoResultException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse getUsers(Integer limit, Integer offset) {
        UserServiceResponse userServiceresponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        List<UserEntity> userEntityList = userRepository.getListUsers(limit, offset);
        userServiceresponse.setData(new UserListEntity(userEntityList));
        return userServiceresponse;
    }

    @Override
    public UserServiceResponse getUsersCount() {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        Integer position = userRepository.getCount();
        userServiceResponse.setData(new ScalarEntity<>(position));
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse userExists(String nickname) {
        UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            UserEntity userEntity = userRepository.getByNickame(nickname);
        } catch (UserRepositoryList.NoResultException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse update(String nickname, UserUpdateEntity userUpdateEntity) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            final UserEntity existingEntity = userRepository.getByNickame(nickname);
            if (userUpdateEntity.getOldPassword() != null) {
                if (!existingEntity.getPasswordHash().equals(userUpdateEntity.getOldPassword())) {
                    userServiceResponse.setStatus(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
                } else {
                    userRepository.update(nickname, userUpdateEntity);
                }
            }
        } catch (UserRepositoryList.NoResultException nRe) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        } catch (UserRepositoryList.ConstraintEmailException cEEx) {
            userServiceResponse.setStatus(UserServiceStatus.CONFLICT_EMAIL_STATE);
        } catch (UserRepositoryList.ConstraintNameException cNEx) {
            userServiceResponse.setStatus(UserServiceStatus.CONFLICT_NAME_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse updateAvatar(String nickname, String avatarPath) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            final UserEntity existingEntity = userRepository.getByNickame(nickname);
            existingEntity.setAvatarPath(avatarPath);
        } catch (UserRepositoryList.NoResultException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }
}
