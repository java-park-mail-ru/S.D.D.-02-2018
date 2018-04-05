package com.colorit.backend.services;

import com.colorit.backend.entities.db.UserEntity;
import com.colorit.backend.entities.input.UserUpdateEntity;
import com.colorit.backend.repositories.UserRepositoryJpa;
import com.colorit.backend.services.responses.UserServiceResponse;
import com.colorit.backend.services.statuses.UserServiceStatus;
import com.colorit.backend.views.entity.representations.UserListEntityRepresentation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.validation.constraints.NotNull;

@Service
public class UserServiceJpa implements IUserService {
    private final UserRepositoryJpa userRepositoryJpa;
    private final PasswordEncoder passwordEncoder;
    private static final Logger LOGGER = LoggerFactory.getLogger(UserServiceJpa.class);


    public UserServiceJpa(@NotNull PasswordEncoder passwordEncoder, @NotNull UserRepositoryJpa userRepositoryJpa) {
        this.passwordEncoder = passwordEncoder;
        this.userRepositoryJpa = userRepositoryJpa;
    }

    @Override
    public UserServiceResponse getUser(String nickname) {
        if (userRepositoryJpa.existsByNickname(nickname)) {
            final UserEntity userEntity = userRepositoryJpa.getByNickname(nickname);
            LOGGER.info("info returned about user {}", nickname);
            return new UserServiceResponse<>(UserServiceStatus.OK_STATE, userEntity.toRepresentation());
        } else {
            return new UserServiceResponse(UserServiceStatus.NOT_FOUND_STATE);
        }
    }

    @Override
    public UserServiceResponse authenticateUser(UserEntity userEntity) {
        final UserEntity existingUserEntity = userRepositoryJpa.getByNickname(userEntity.getNickname());
        if (!passwordEncoder.matches(userEntity.getPasswordHash(), existingUserEntity.getPasswordHash())) {
            LOGGER.info("cant authenticate user {}", userEntity.getNickname());
            return new UserServiceResponse(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
        }

        LOGGER.info("authenticated user {}", userEntity.getNickname());
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse userExists(String nickname) {
        if (!userRepositoryJpa.existsByNickname(nickname)) {
            LOGGER.info("no such user {}", nickname);
            return new UserServiceResponse(UserServiceStatus.NAME_MATCH_ERROR_STATE);
        }
        LOGGER.info("user found {}", nickname);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse createUser(UserEntity userEntity) {
        final String userPassword = userEntity.getPasswordHash();
        userEntity.setPasswordHash(passwordEncoder.encode(userPassword));
        userRepositoryJpa.save(userEntity);

        LOGGER.info("user created", userEntity.getNickname());
        return new UserServiceResponse(UserServiceStatus.CREATED_STATE);
    }

    @Override
    public UserServiceResponse updateEmail(String nickname, String email) {
        final UserEntity existingEntity = userRepositoryJpa.getByNickname(nickname);
        existingEntity.setEmail(email);
        userRepositoryJpa.update(existingEntity);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse updatePassword(String nickname, String oldPassword, String newPassword) {
        final UserEntity existingEntity = userRepositoryJpa.getByNickname(nickname);

        if (!passwordEncoder.matches(oldPassword, existingEntity.getPasswordHash())) {
            return new UserServiceResponse(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
        }

        existingEntity.setPasswordHash(passwordEncoder.encode(newPassword));
        userRepositoryJpa.update(existingEntity);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse updateNickname(String nickname, String newNickname) {
        final UserEntity existingEntity = userRepositoryJpa.getByNickname(nickname);

        existingEntity.setNickname(newNickname);
        userRepositoryJpa.update(existingEntity);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse updateAvatar(String nickname, String avatar) {
        final UserEntity existingEntity = userRepositoryJpa.getByNickname(nickname);

        existingEntity.setAvatarPath(avatar);
        userRepositoryJpa.update(existingEntity);
        return new UserServiceResponse<>(UserServiceStatus.OK_STATE, avatar);
    }

    @Override
    public UserServiceResponse update(String nickname, UserUpdateEntity updateEntity) {
        final UserEntity existingEntity = userRepositoryJpa.getByNickname(nickname);

        if (updateEntity.getOldPassword() != null) {
            if (!passwordEncoder.matches(updateEntity.getOldPassword(), existingEntity.getPasswordHash())) {
                return new UserServiceResponse(UserServiceStatus.PASSWORD_MATCH_ERROR_STATE);
            } else {
                updateEntity.setNewPassword(passwordEncoder.encode(updateEntity.getNewPassword()));
            }
        }

        existingEntity.copy(updateEntity);
        userRepositoryJpa.update(existingEntity);
        return new UserServiceResponse(UserServiceStatus.OK_STATE);
    }

    @Override
    public UserServiceResponse getUsers(Integer limit, Integer offset) {
        return new UserServiceResponse<>(UserServiceStatus.OK_STATE,
                new UserListEntityRepresentation(userRepositoryJpa.getUsers(limit, offset)));
    }

    @Override
    public UserServiceResponse getUsersCount() {
        return new UserServiceResponse<>(UserServiceStatus.OK_STATE, userRepositoryJpa.count());
    }

    @Override
    public UserServiceResponse getPosition(String nickname) {
        final UserEntity existingEntity = userRepositoryJpa.getByNickname(nickname);
        return new UserServiceResponse<>(UserServiceStatus.OK_STATE, userRepositoryJpa.getPosition(nickname));
    }
}