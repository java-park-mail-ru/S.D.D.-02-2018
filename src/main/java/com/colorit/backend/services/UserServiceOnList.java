package com.colorit.backend.services;

import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.entities.input.UserUpdateEntity;
import com.colorit.backend.repositories.UserRepositoryList;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import com.colorit.backend.views.entity.representations.UserListEntityRepresentation;
import com.colorit.backend.views.entity.representations.UserEntityRepresentation;
import com.colorit.backend.views.input.SignInView;
import com.colorit.backend.views.input.SignUpView;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceOnList implements IUserService {
    private final UserRepositoryList userRepository = new UserRepositoryList();

    @Override
    public UserServiceResponse getUser(String nickname) {
        final UserServiceResponse<UserEntityRepresentation> userServiceResponse =
                new UserServiceResponse<>(UserServiceStatus.OK_STATE);
        try {
            final UserEntity userEntity = userRepository.getByNickame(nickname);
            userServiceResponse.setData(userEntity.toRepresentation());
        } catch (UserRepositoryList.NoResultException nRx) {
            userServiceResponse.setStatus(UserServiceStatus.NOT_FOUND_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse authenticateUser(SignInView user) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            final UserEntity existingEntity = userRepository.getByNickame(user.getNickname());
            if (!existingEntity.getPasswordHash().equals(user.getPassword())) {
                userServiceResponse.setStatus(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
            }
        } catch (UserRepositoryList.NoResultException nRx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse createUser(SignUpView user) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.CREATED_STATE);
        try {
            UserEntity userEntity = UserEntity.fromView(user);
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
    public UserServiceResponse updateNickname(String nickname, String newNickname) {
        final UserServiceResponse userServiceResponse = new UserServiceResponse(UserServiceStatus.OK_STATE);
        try {
            userRepository.changeNickname(nickname, newNickname);
        } catch (UserRepositoryList.ConstraintNameException cNEx) {
            userServiceResponse.setStatus(UserServiceStatus.CONFLICT_NAME_STATE);
        } catch (UserRepositoryList.NoResultException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
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
        final UserServiceResponse<Long> userServiceResponse = new UserServiceResponse<>(UserServiceStatus.OK_STATE);
        try {
            Long position = userRepository.getPosition(nickname);
            userServiceResponse.setData(position);
        } catch (UserRepositoryList.NoResultException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }

    @Override
    public UserServiceResponse getUsers(Integer limit, Integer offset) {
        UserServiceResponse<UserListEntityRepresentation> userServiceresponse = new UserServiceResponse<>(UserServiceStatus.OK_STATE);
        List<UserEntity> userEntityList = userRepository.getListUsers(limit, offset);
        userServiceresponse.setData(new UserListEntityRepresentation(userEntityList));
        return userServiceresponse;
    }

    @Override
    public UserServiceResponse getUsersCount() {
        final UserServiceResponse<Integer> userServiceResponse = new UserServiceResponse<>(UserServiceStatus.OK_STATE);
        Integer position = userRepository.getCount();
        userServiceResponse.setData(position);
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
        final UserServiceResponse<String> userServiceResponse = new UserServiceResponse<>(UserServiceStatus.OK_STATE);
        try {
            final UserEntity existingEntity = userRepository.getByNickame(nickname);
            existingEntity.setAvatarPath(avatarPath);
            userServiceResponse.setData(avatarPath);
        } catch (UserRepositoryList.NoResultException nREx) {
            userServiceResponse.setStatus(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        return userServiceResponse;
    }
}
